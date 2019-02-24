SET client_encoding TO 'UTF8';

SET search_path = tribalwars_owner,pg_catalog;

create table world
(
  world_id   varchar not null
    constraint world_pk
      primary key,
  world_name varchar
);

create table player
(
  world_id  varchar      not null
    constraint player_world_world_id_fk
      references world
      on update cascade on delete cascade,
  player_id integer      not null,
  name      varchar(256) not null,
  tribe_id  integer      not null,
  points    integer      not null,
  rank      integer      not null,
  constraint world_id_player_pk
    primary key (world_id, player_id)
);

create table village
(
  world_id   varchar      not null
    constraint village_world_world_id_fk
      references world
      on update cascade on delete cascade,
  village_id integer      not null,
  name       varchar(255) not null,
  x          smallint     not null,
  y          smallint     not null,
  player_id  integer      not null,
  points     integer      not null,
  rank       integer      not null,
  constraint village_pk
    primary key (world_id, village_id),
  constraint village_player_player_id_world_id_fk
    foreign key (player_id, world_id) references player (player_id, world_id)
      on update cascade on delete cascade
);

create unique index village_world_id_player_id_village_id_uindex
  on village (world_id, player_id, village_id);

create table tribe
(
  tribe_id   integer      not null,
  name       varchar(255) not null,
  tag        varchar(10)  not null,
  members    integer      not null,
  villages   integer      not null,
  points     integer      not null,
  all_points integer      not null,
  rank       integer      not null,
  world_id   varchar      not null
    constraint tribe_world_world_id_fk
      references world
      on update cascade on delete cascade,
  constraint tribe_pk
    primary key (world_id, tribe_id)
);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table users
(
  id              varchar(64) default uuid_generate_v4() not null
    constraint users_pkey
      primary key,
  ipadress        char(45)                               not null,
  first_request   timestamp                              not null,
  last_request    timestamp                              not null,
  request_counter integer     default 1                  not null,
  constraint users_id_uindex
    unique (id, ipadress)
);