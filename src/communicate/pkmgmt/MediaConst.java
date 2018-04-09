package communicate.pkmgmt;

public class MediaConst {

	public static final class ProgramType {
		public static final int FILM= 4;
		public static final int TVSERIES = 7;
		public static final int ALBUM = 8;
		public static final int SPECIAL_SINGLE=12;
		public static final int SPECIAL_MULTI=13;
		public static final int SPECIAL=14;
		public static final int PIC=99;
		// 广告视频10，广告图片11
	}
	public static final class ArtistType{
		public static final String MAN_LBL="男歌手";
		public static final String WOMEN_LBL="女歌手";
		public static final String GROUP_LBL="乐队组合";

		public static final String MAN="11";
		public static final String WOMEN="12";
		public static final String GROUP="13";

		public static String getLbl(String value){
			if(value.equals(MAN)) return MAN_LBL;
			if(value.equals(WOMEN)) return WOMEN_LBL;
			if(value.equals(GROUP)) return GROUP_LBL;
			return "";
		}
	}
	public static final class CategoryId{
		public static final String MOVIE_ID="1000";
		public static final String TV_ID="2000";
		public static final String MUSIC_ID="3000";
		public static final String SPECIAL_ID="4000";
	}
	public static final class UpdateStatus{
		public static final String UPDATIING_LBL = "更新中";
		public static final String FINISHED_LBL = "已完结";

		public static final int UPDATIING = 1;
		public static final int FINISHED = 0;

		public static String getLbl(int value){
			if(value==UPDATIING)return UPDATIING_LBL;
			if(value==FINISHED)return FINISHED_LBL;
			return "";
		}
	}
	public static final class Quality{
		public static final Integer ALL=0;
		public static final Integer SD=1;
		public static final Integer HD=2;
		public static final Integer TD=3;

		public static String getLbl(Integer value){
			if(value.intValue()==SD.intValue()) return "标清";
			if(value.intValue()==HD.intValue()) return "高清";
			if(value.intValue()==TD.intValue()) return "3D";
			return "";
		}
	}
	public static final class Language{
		public static final Integer CHINESE=1;
		public static final Integer YUEYU=2;
		public static final Integer ENGLISH=3;
		public static final Integer JAPANESE=4;
		public static final Integer KOREAN=5;
		public static final Integer OTHER=6;

		public static final String CHINESE_LBL="国语";
		public static final String YUEYU_LBL="粤语";
		public static final String ENGLISH_LBL="英语";
		public static final String JAPANESE_LBL="日语";
		public static final String KOREAN_LBL="韩语";
		public static final String OTHER_LBL="其他语种";

		public static String getLbl(Integer value){
			if(value.intValue()==CHINESE.intValue()) return CHINESE_LBL;
			if(value.intValue()==YUEYU.intValue()) return YUEYU_LBL;
			if(value.intValue()==ENGLISH.intValue()) return ENGLISH_LBL;
			if(value.intValue()==JAPANESE.intValue()) return JAPANESE_LBL;
			if(value.intValue()==KOREAN.intValue()) return KOREAN_LBL;
			if(value.intValue()==OTHER.intValue()) return OTHER_LBL;
			return "";
		}

		public static Integer getValue(String lbl){
			if(lbl.equals(CHINESE_LBL))return CHINESE;
			if(lbl.equals(YUEYU_LBL))return YUEYU;
			if(lbl.equals(ENGLISH_LBL))return ENGLISH;
			if(lbl.equals(JAPANESE_LBL))return JAPANESE;
			if(lbl.equals(KOREAN_LBL))return KOREAN;
			return OTHER;
		}
	}
}
