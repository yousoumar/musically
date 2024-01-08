create table singers
(
	id         bigserial not null,
	singer_id  uuid      not null,
	first_name varchar(255),
	last_name  varchar(255),
	primary key (id)
);

create table songs
(
	id           bigserial not null,
	rating       float,
	release_year integer,
	song_id      uuid      not null,
	title        varchar(255),
	primary key (id)
);

create table singer_song
(
	singer_id bigint not null,
	song_id   bigint not null,
	primary key (singer_id, song_id),
	foreign key (song_id) references songs (id),
	foreign key (singer_id) references singers (id)
);
