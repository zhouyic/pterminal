CREATE TABLE TML_AD (
	adId         VARCHAR(64),
	adType       INTEGER DEFAULT 0,/*10:ad 11:pic */
	listName     VARCHAR(64),
	CXMLName     VARCHAR(64),
	outDate      VARCHAR(64),
	inTime       VARCHAR(64),
	PRIMARY KEY(adId)
);

CREATE TABLE TML_UAD (
	uadId         VARCHAR(64),
	listName     VARCHAR(64),
	inTime       VARCHAR(64),
	PRIMARY KEY(uadId)
);