CREATE TABLE TML_LOG(
	id              VARCHAR(32), /*tmlId+日期+4位随机数*/
	tmlId           VARCHAR(16),
	doAction        VARCHAR(16),
	doObject        VARCHAR(128),
	doTime		    VARCHAR(64),
	session         VARCHAR(32),
	startTime       VARCHAR(64),
	endTime         VARCHAR(64),
	doResult        VARCHAR(16),
	exception   	VARCHAR(256),
	inTime       	VARCHAR(64),
	PRIMARY KEY(id)
);