drop table paymentlist;
drop table cartlist;
drop table adminlist;
drop table userlist;
drop table productlist;


-- 상품 테이블 작성
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


-- 회원 테이블 작성
create table userlist(
  userNumber int primary key,
  userId varchar2(20) not null unique,
  userPassword varchar2(20) not null,
  userName varchar2(20) not null
);


insert into userlist values(1, 'user1', 'user1', '유저1');
insert into userlist values(2, 'user2', 'user2', '유저2');
insert into userlist values(3, 'user3', 'user3', '유저3');
insert into userlist values(4, 'choiwj1012', 'choiwj1012', '최원재');


-- 관리자 테이블 작성
create table adminlist(
  adminNumber int primary key,
  adminId varchar2(20) not null unique,
  adminPassword varchar2(20) not null,
  adminName varchar2(20) not null,
  authority int not null
);


-- 권한번호 1은 점장, 2는 일반직원
insert into adminlist values(1, 'admin1', 'admin1', '관리자1', 1);
insert into adminlist values(2, 'admin2', 'admin2', '관리자2', 2);
insert into adminlist values(3, 'admin3', 'admin3', '관리자3', 3);


-- 카트 테이블 작성
create table cartlist(
  orderNumber int primary key,
  productNumber int references productlist(productNumber),
  userNumber int references userlist(userNumber),
  orderCount int not null,
  orderDate Date default sysdate,
  isPayment int not null
);


-- 결제 테이블 작성
create table paymentlist(
  paymentListNumber int primary key,
  userNumber int references userlist(userNumber),
  productNumber int references productlist(productNumber),
  paymentCount int,
  paymentMethod int not null,
  paymentDate Date default sysdate
);

select * from productlist;
select * from userlist;
select * from adminlist;
select * from cartlist;
select * from paymentlist;
