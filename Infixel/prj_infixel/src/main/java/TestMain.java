import VO.PostVO;

public class TestMain {

	public static void main(String[] args) {
		PostVO vo = new PostVO();
		String[] tags = {"a", "b"};
		vo.setTags(tags);
		
		System.out.println(vo.getTags());
	}

}
