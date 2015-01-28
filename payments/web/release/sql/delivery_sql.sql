create table Delivery (
    id number(19,0) not null,
    version number(19,0),
    creationTime timestamp not null,
    orderId varchar2(255 char) not null,
    criteria varchar2(500 char) not null,
    ownerFirstName varchar2(255 char),
    ownerLastName varchar2(255 char),
    productName varchar2(50 char) not null,
    serialNumber varchar2(255 char) not null unique,
    primary key (id)
);

