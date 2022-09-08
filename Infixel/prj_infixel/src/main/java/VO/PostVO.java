package VO;

import java.util.List;

import lombok.Data;

@Data
public class PostVO {
	private String title;
	private String user;
	private int download;
	private String fileName;
	private String link;
	private String date;
	private int postNum;
	private int views;
	private List<String> tags;
	
	
	public void setTags(String[] putTags) {
		for(int i = 0; i < putTags.length; i++) {
			tags.add(putTags[i]);
		}
	}
	
	public List<String> getTags() {
		return this.tags;
	}
	
}
