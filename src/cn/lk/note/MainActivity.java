package cn.lk.note;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements DialogInterface.OnClickListener, View.OnClickListener {
	
	private TextView tvTitle;
	private EditText etEn;
	private EditText etZh;
	private Button btnSubmit;
	private Button btnBack;
	private ListView lvWords;
	private WordAdapter adapter;
	private List<Word> words;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvTitle = (TextView) findViewById(R.id.tv_title);
		etEn = (EditText) findViewById(R.id.et_en);
		etZh = (EditText) findViewById(R.id.et_zh);
		btnSubmit = (Button) findViewById(R.id.btn_submit);
		btnBack = (Button) findViewById(R.id.btn_back);
		lvWords = (ListView) findViewById(R.id.lv_words);
		
		WordDao dao = new WordDao(this);
		words = dao.query(null, null, "_id desc");
		adapter = new WordAdapter(this, words);
		lvWords.setAdapter(adapter);
		
		btnSubmit.setOnClickListener(this);
	    btnBack.setOnClickListener(this);
		
		registerForContextMenu(lvWords);
	}
	
	private static final int ACTION_EDIT = 001;
	private static final int ACTION_DELETE = 002;
	int position;
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		position = info.position;
		
		menu.add(Menu.NONE, ACTION_EDIT, Menu.NONE, "�༭ "+words.get(position).getEn());
		menu.add(Menu.NONE, ACTION_DELETE, Menu.NONE, "ɾ�� "+words.get(position).getEn());
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	WordDao dao = new WordDao(this);
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ContextMenuInfo menuInfo = item.getMenuInfo();
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		position = info.position;
		
		switch (item.getItemId()) {
		case ACTION_EDIT:
			isEditMode = true;
			Word word = words.get(position);
			editId = word.getId();
			etEn.setText(word.getEn());
			etZh.setText(word.getZh());
			btnSubmit.setText("�޸�");
			btnBack.setVisibility(View.VISIBLE);
			tvTitle.setText("�༭ "+word.getEn()+" ����Ϣ");
			break;

		case ACTION_DELETE:
			showDeleteDialog();
			alertDialog.show();
			break;
		}
		return super.onContextItemSelected(item);
	}
	/**
	 * ɾ�����ܶԻ�����ʾ
	 */
	private AlertDialog alertDialog;
	private void showDeleteDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("ɾ��")
		.setMessage("��ȷ��ɾ�� "+words.get(position).getEn())
		.setPositiveButton("ȷ��", this)
		.setNegativeButton("ȡ��", null)
		.setCancelable(false);
		alertDialog = builder.create();
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		
			int affectedRows = dao.delete(words.get(position).getId());
			if(affectedRows > 0) {
				words.clear();
				words.addAll(dao.query(null, null, "_id desc"));
				adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(this, "ɾ��ʧ�ܣ�����ϵ����Ա��", Toast.LENGTH_SHORT).show();
			}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    private boolean isEditMode;
    private long editId;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			isEditMode = false;
			tvTitle.setText("�����뵥����Ϣ :");
			etEn.setText("");
			etZh.setText("");
			btnSubmit.setText("���");
			btnBack.setVisibility(View.GONE);
			break;

		case R.id.btn_submit:
			String en = etEn.getText().toString().trim();
			String zh = etZh.getText().toString().trim();
			
			Word word = new Word();
			word.setEn(en);
			word.setZh(zh);
			
			WordDao dao = new WordDao(this);
			
			if(isEditMode) {
				word.setId(editId);
				int affectedRows = dao.update(word);
				isEditMode = false;
				tvTitle.setText("�����뵥����Ϣ :");
				etEn.setText("");
				etZh.setText("");
				btnSubmit.setText("���");
				btnBack.setVisibility(View.GONE);
				if(affectedRows > 0) {
					words.clear();
					words.addAll(dao.query(null, null, "_id desc"));
					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(this, "ɾ��ʧ�ܣ�����ϵ����Ա��", Toast.LENGTH_SHORT).show();
				}
			} else {
				long id = dao.insert(word);
				
				if(id == -1) {
					Toast.makeText(this, "�������ʧ�ܣ�", Toast.LENGTH_SHORT).show();
				} else {
					etEn.setText("");
					etZh.setText("");
					
					words.clear();
					words.addAll(dao.query(null, null, "_id desc"));
					adapter.notifyDataSetChanged();
				}
			}
			break;
		}
		
	}
	
}
