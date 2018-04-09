package entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
/**
 * 单终端在线时长
 * @author zhouyc
 *
 */
@DynamoDBTable(tableName="Singel_Tml_Online_Duration_Test")
public class SingleStbOnlineDuration {
	private String tmlId;  //终端ID
	private  String createTime;  //插入日期
	private String onlineDuration;//在线时长
	private String tmlCount;//上线次数
	private String onlineTime; //在线时间
	private String offlineTime;//下线时间
	@DynamoDBHashKey(attributeName="tmlId")
	public String getTmlId() {
		return tmlId;
	}
	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}
	@DynamoDBAttribute(attributeName="createTime")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@DynamoDBRangeKey(attributeName="onlineTime")
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	@DynamoDBAttribute(attributeName="onlineDuration")
	public String getOnlineDuration() {
		return onlineDuration;
	}
	public void setOnlineDuration(String onlineDuration) {
		this.onlineDuration = onlineDuration;
	}
	@DynamoDBAttribute(attributeName="tmlCount")
	public String getTmlCount() {
		return tmlCount;
	}
	public void setTmlCount(String tmlCount) {
		this.tmlCount = tmlCount;
	}
	@DynamoDBAttribute(attributeName="offlineTime")
	public String getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}
	
	@Override  
	public boolean equals(Object obj) {  
		SingleStbOnlineDuration singleStbOnlineDuration=(SingleStbOnlineDuration)obj;   
		return tmlId.equals(singleStbOnlineDuration.tmlId) && onlineTime.equals(singleStbOnlineDuration.onlineTime);   
	}  
	@Override  
	public int hashCode() {  
		String in = tmlId + onlineTime;  
		return in.hashCode();  
	} 
	@Override
	public String toString() {
		return "SingleStbOnlineDuration [tmlId=" + tmlId + ", createTime="
				+ createTime + ", onlineDuration=" + onlineDuration
				+ ", onlineTime=" + onlineTime + ", offlineTime=" + offlineTime
				+ "]";
	}
}
