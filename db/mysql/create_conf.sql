CREATE TABLE DOM_CONF(
	dConfId         INTEGER NOT NULL AUTO_INCREMENT,
	DOMID			INTEGER,
	confItemId		INTEGER,
	IP				VARCHAR(50),
	Port			VARCHAR(50),
	PRIMARY KEY (dConfId)
);
CREATE TABLE CONF_ITEM(
	confItemId      INTEGER NOT NULL AUTO_INCREMENT,
	confName        VARCHAR(50),
	confDesc        VARCHAR(50),
	confType        INTEGER,
	confTag         INTEGER,
	IP              VARCHAR(50),
	Port            VARCHAR(50),
	PRIMARY KEY (confItemId)
);
ALTER TABLE TML_BASE ADD telNo VARCHAR(32);