drop table paymentlist;
drop table cartlist;
drop table adminlist;
drop table userlist;
drop table productlist;


-- ��ǰ ���̺� �ۼ�
create table productlist(
  productNumber int primary key,
  productName varchar2(50) not null unique,
  productPrice int not null,
  productComment varchar2(50) not null,
  productVendor varchar2(50) not null
);


insert into productlist values(1,'Gallexy_Note_3',345000,'smartPhone','Samsung');
insert into productlist values(2,'iPhone_6', 780000, 'smartPhone', 'apple');
insert into productlist values(3,'LG_WineSmart', 153000, 'smartFolder', 'LG');
insert into productlist values(4,'iPad_Pro', 982000, 'tablePc', 'apple');
insert into productlist values(5,'Gellexy_Note_7',1123000, 'smartPhone', 'Samsung');


-- ȸ�� ���̺� �ۼ�
create table userlist(
  userNumber int primary key,
  userId varchar2(20) not null unique,
  userPassword varchar2(20) not null,
  userName varchar2(20) not null
);


insert into userlist values(1, 'user1', 'user1', '����1');
insert into userlist values(2, 'user2', 'user2', '����2');
insert into userlist values(3, 'user3', 'user3', '����3');
insert into userlist values(4, 'choiwj1012', 'choiwj1012', '�ֿ���');


-- ������ ���̺� �ۼ�
create table adminlist(
  adminNumber int primary key,
  adminId varchar2(20) not null unique,
  adminPassword varchar2(20) not null,
  adminName varchar2(20) not null,
  authority int not null
);


-- ���ѹ�ȣ 1�� ����, 2�� �Ϲ�����
insert into adminlist values(1, 'admin1', 'admin1', '������1', 1);
insert into adminlist values(2, 'admin2', 'admin2', '������2', 2);
insert into adminlist values(3, 'admin3', 'admin3', '������3', 3);


-- īƮ ���̺� �ۼ�
create table cartlist(
  orderNumber int primary key,
  productNumber int references productlist(productNumber),
  userNumber int references userlist(userNumber),
  orderCount int not null,
  orderDate Date default sysdate
);


-- ���� ���̺� �ۼ�
create table paymentlist(
  paymentListNumber int primary key,
  orderNumber int references cartlist(orderNumber),
  paymentDate Date default sysdate
);

