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
@DynamoDBTable(tableName="Single_Tml_Online_Duration_Total_Test")
public class SingleStbOnlineDurationTotal {
	private String tmlId;  //终端ID
	private String tmlCount;//上线次数
	private  String createTime;  //插入日期
	private String onlineDuration;//在线时长
	@DynamoDBHashKey(attributeName="tmlId")
	public String getTmlId() {
		return tmlId;
	}
	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}
	@DynamoDBRangeKey(attributeName="createTime")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	@Override
	public String toString() {
		return "SingleStbOnlineDurationTotal [tmlId=" + tmlId + ", createTime="
				+ createTime + ", onlineDuration=" + onlineDuration + "]";
	}
	
	
}
