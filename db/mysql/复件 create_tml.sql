

CREATE table TML_SYS_INFO (
	tmlId           VARCHAR(16),
	tmlVersion      VARCHAR(32),
	leftDiskSize    VARCHAR(32),
	ableMem         VARCHAR(32),
	portalVersion   VARCHAR(32),
	portalUrl       VARCHAR(64),
	tmlPlayling     VARCHAR(64),
	tmlDownling     VARCHAR(256),
	tmlApps         VARCHAR(256),
	tmlSpelist      VARCHAR(256),
	updateDate      VARCHAR(64),
	PRIMARY KEY(tmlId)
);