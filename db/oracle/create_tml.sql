CREATE TABLE TML_ORDER(
	orderId         VARCHAR(64),
	tmlId	        VARCHAR(64),
	serviceType     INTEGER DEFAULT 0,
	programId       VARCHAR(64),
	contentId		VARCHAR(64),
	contentName     VARCHAR(64),
	movieName       VARCHAR(64),
	episodeIndex    INTEGER DEFAULT 0,
	episodes        INTEGER DEFAULT 0,
	snap            VARCHAR(64),
	movieSize       INTEGER DEFAULT 0,
	chargeMode	    INTEGER DEFAULT 0,
	count   	    INTEGER DEFAULT 0,
	listName        VARCHAR(64),
	CXMLName        VARCHAR(64),
	licenseName     VARCHAR(64),
	parentName      VARCHAR(64),
	startTime       VARCHAR(32) DEFAULT '0000-00-00 00:00:00',
	endTime         VARCHAR(32) DEFAULT '0000-00-00 00:00:00',
	showTime        VARCHAR(16) DEFAULT '0000-00-00',
	tarFileName     VARCHAR(64),
	idxFileName     VARCHAR(64),
	runTime         INTEGER DEFAULT 0,
	actors        	VARCHAR(64),
	pLauguage       VARCHAR(32),
	screenFormat    VARCHAR(32),
	portalType      INTEGER DEFAULT 0,
	status          INTEGER DEFAULT 0,
	inTime          VARCHAR(32),
	PRIMARY KEY (orderId)
);

CREATE TABLE TML_BASE(
	groupId         INTEGER DEFAULT 0,
	unitId          INTEGER DEFAULT 0,
	tmlId           VARCHAR(16),
	tmlType         INTEGER DEFAULT 0,
	customerId      VARCHAR(64),
	customerType    VARCHAR(16),
	customerPwd     VARCHAR(64),
	oldCustomerId   VARCHAR(64),
	oldCustomerPwd  VARCHAR(64),
	inOperator   	VARCHAR(32),
	inTime       	VARCHAR(64),
	tmlStatus    	INTEGER DEFAULT 0,
	ipAddr       	VARCHAR(64),
	onTime       	VARCHAR(64),
	outTime      	VARCHAR(64),
	description     VARCHAR(128),
	isSpe           INTEGER DEFAULT 0,
	PRIMARY KEY(tmlId)
);

CREATE table TML_SPE (
	tmlId           VARCHAR(16),
	speIp       	VARCHAR(64),
	PRIMARY KEY(tmlId)
);

CREATE TABLE TML_SERVER_LIST(
	id           INTEGER,
	ip        	 VARCHAR(64),
	port      	 INTEGER DEFAULT 0,
	tag		 	 INTEGER DEFAULT 0,  /*ols:0; spe:1; AppPortal_URL:2; STBPortal_URL:3*/
	netType      INTEGER DEFAULT 0,  /*公网:0; 内网:1;*/
	groupId      INTEGER DEFAULT 0,
	unitId		 INTEGER DEFAULT 0,
	area         VARCHAR(128),
	inTime       VARCHAR(64),
	inOperator   VARCHAR(32),
	description  VARCHAR(128),
	PRIMARY KEY(id)
);

CREATE TABLE TML_BILLING(
	orderId			VARCHAR(64),
	tmlId           VARCHAR(16),
	bossId      	VARCHAR(32),
	contentId		VARCHAR(64),
	contentName     VARCHAR(64),
	releaseId     	VARCHAR(64),
	amount		    INTEGER DEFAULT 0,
	syncServiceId   VARCHAR(64),
	status      	INTEGER DEFAULT 0,
	statusResult    INTEGER DEFAULT 0,
	inTime       	VARCHAR(64),
	PRIMARY KEY(orderId)
);
