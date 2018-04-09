DROP TABLE IF EXISTS TML_BASE;
CREATE TABLE TML_BASE(
	tml_id VARCHAR(64),
	tml_type INT,
	model VARCHAR(128),
	publish_time VARCHAR(128),
	on_time BIGINT,
	status INT,
	heartbeat_rate INT,
	current_version VARCHAR(128),
	app_status INT,
	tml_conf_id INT,
	content_name VARCHAR(512),
	service_type INT,
	PRIMARY KEY(tml_id)
) DEFAULT CHARACTER SET UTF8;

