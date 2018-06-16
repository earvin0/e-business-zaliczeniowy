# --- !Ups


create table "category" (
  "id" integer not null primary key autoincrement,
  "name" varchar not null
);

create table "product" (
  "id" integer not null primary key autoincrement,
  "name" varchar not null,
  "description" text not null,
  category int not null,
  foreign key(category) references category(id)
);

CREATE TABLE keyword
(
    word varchar PRIMARY KEY NOT NULL,
    occurrences int DEFAULT 0
);

create table user
(
  id   integer
    primary key
  autoincrement,
  name varchar
);

CREATE TABLE "order"
(
    id integer PRIMARY KEY AUTOINCREMENT,
    userID integer,
    paid integer DEFAULT 0,
    CONSTRAINT userID___fk FOREIGN KEY (userID) REFERENCES user (id)
);

CREATE TABLE cart
(
    userID integer,
    productID integer,
    quantity integer,
    CONSTRAINT iserID___fk FOREIGN KEY (userID) REFERENCES user (id)
);

CREATE TABLE orderedProduct
(
    orderID integer,
    productID integer,
    quantity integer,
    CONSTRAINT orderID_fk FOREIGN KEY (orderID) REFERENCES "order" (id)
);

CREATE TABLE review
(
    userID integer,
    productID integer,
    grade integer,
    review text,
    CONSTRAINT userID_fk FOREIGN KEY (userID) REFERENCES user (id),
    CONSTRAINT productID_fk FOREIGN KEY (productID) REFERENCES product (id)
);

# --- !Downs

drop table "product" if exists;
drop table "category" if exists;
drop table "keyword" if exists;
drop table "user" if exists;
drop table "order" if exists;
drop table "cart" if exists;
drop table "orderedProduct" if exists;
drop table "review" if exists;