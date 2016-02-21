package cn.lk.note;

public class Word {
	private long id;
	private String en;
	private String zh;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public String getZh() {
		return zh;
	}
	public void setZh(String zh) {
		this.zh = zh;
	}
	
	@Override
	public String toString() {
		return "Word[id="+id+",en="+en+",zh="+zh+"]";
	}

}
