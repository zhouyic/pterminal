package communicate;

public interface IMemCacheService {
	public int addOnlineTml(String tmlId, String time);
	public int deleteOnlineTml(String tmlId);

}
