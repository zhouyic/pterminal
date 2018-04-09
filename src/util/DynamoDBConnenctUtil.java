package util;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3Client;

public class DynamoDBConnenctUtil {
	private static String access_key_id = "AKIAIHB4FXVSH7N2BDUQ";
	private static String secret_access_key = "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";
	private static BasicAWSCredentials awsCreds = new BasicAWSCredentials(access_key_id, secret_access_key);
	private static AmazonDynamoDBClient client = null;
	private static DynamoDBMapper mapper = null;
//	private static String bucketName = "dspdps3";
	private static AmazonS3Client s3 =null;
	private static Region region=null;
	static{
		client = new AmazonDynamoDBClient(awsCreds);
		region = Region.getRegion(Regions.EU_CENTRAL_1);
		client.setRegion(region);
		mapper=new DynamoDBMapper(client);
    }
	public static DynamoDBMapper getMappper(){
		return mapper;
	}
	public static AmazonDynamoDBClient getAmazonDynamoDBClient(){
		return client;
	}
	public static void main(String[] args) {
		System.out.println(getMappper());
	}
}
