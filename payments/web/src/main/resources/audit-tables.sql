drop table clickstream_requests;
drop table clickstream;
CREATE TABLE clickstream
(
  streamhost character varying(255),
  referrer character varying(255),
  lastreqtime timestamp ,
  starttime timestamp,
  sessionid character varying(255) NOT NULL,
  CONSTRAINT "SESSIONPK" PRIMARY KEY (sessionid)
);

CREATE TABLE clickstream_requests
(
  sessionid character varying(255) NOT NULL,
  protocol character varying(8),
  qrystring character varying(255),
  streamuser character varying(255),
  uri character varying(255),
  server character varying(255),
  port integer,
  reqtime timestamp,
  CONSTRAINT "cs_requests_SESSIONID_fkey" FOREIGN KEY (sessionid)
      REFERENCES clickstream (sessionid)
);