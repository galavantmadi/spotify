PGDMP                         {           postgres    13.2    13.2 1                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    13442    postgres    DATABASE     l   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
    DROP DATABASE postgres;
                postgres    false                       0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                   postgres    false    3075                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                postgres    false                       0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   postgres    false    4            �            1259    16394    account    TABLE     �   CREATE TABLE public.account (
    id bigint NOT NULL,
    username text,
    password text,
    birth_of_date text,
    token text
);
    DROP TABLE public.account;
       public         heap    postgres    false    4            �            1259    16400    account_id_seq    SEQUENCE     �   ALTER TABLE public.account ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    201            �            1259    16402    artist    TABLE     �   CREATE TABLE public.artist (
    id integer NOT NULL,
    name text,
    family text,
    "nickName" text,
    "bornCity" text,
    "bornDate" text
);
    DROP TABLE public.artist;
       public         heap    postgres    false    4            �            1259    16408    artist_album    TABLE     ~   CREATE TABLE public.artist_album (
    id integer NOT NULL,
    artist_id integer,
    album_name text,
    cover_img text
);
     DROP TABLE public.artist_album;
       public         heap    postgres    false    4            �            1259    16414    artist_album_id_seq    SEQUENCE     �   ALTER TABLE public.artist_album ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.artist_album_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    204    4            �            1259    16418    artist_song    TABLE     u   CREATE TABLE public.artist_song (
    id integer NOT NULL,
    album_id integer,
    url text,
    song_name text
);
    DROP TABLE public.artist_song;
       public         heap    postgres    false    4            �            1259    16424    artist_song_id_seq    SEQUENCE     �   ALTER TABLE public.artist_song ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.artist_song_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    206            �            1259    16426 	   downloads    TABLE     e   CREATE TABLE public.downloads (
    account_id text,
    game_id text,
    download_count integer
);
    DROP TABLE public.downloads;
       public         heap    postgres    false    4            �            1259    24578    favorite_song    TABLE     i   CREATE TABLE public.favorite_song (
    id integer NOT NULL,
    user_id integer,
    song_id integer
);
 !   DROP TABLE public.favorite_song;
       public         heap    postgres    false    4            �            1259    24576    favorite_song_id_seq    SEQUENCE     �   ALTER TABLE public.favorite_song ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.favorite_song_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    217            �            1259    16432    game    TABLE     �   CREATE TABLE public.game (
    id text NOT NULL,
    title text,
    developer text,
    genre text,
    price double precision,
    release_year integer,
    controller_support boolean,
    reviews integer,
    size integer,
    file_path text
);
    DROP TABLE public.game;
       public         heap    postgres    false    4            �            1259    16438 	   play_list    TABLE     _   CREATE TABLE public.play_list (
    id integer NOT NULL,
    user_id integer,
    name text
);
    DROP TABLE public.play_list;
       public         heap    postgres    false    4            �            1259    16444    play_list_id_seq    SEQUENCE     �   ALTER TABLE public.play_list ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.play_list_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    210            �            1259    16446    play_list_item    TABLE     v   CREATE TABLE public.play_list_item (
    id integer NOT NULL,
    play_list_id integer,
    artist_song_id integer
);
 "   DROP TABLE public.play_list_item;
       public         heap    postgres    false    4            �            1259    16449    play_list_item_id_seq    SEQUENCE     �   ALTER TABLE public.play_list_item ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.play_list_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    212    4            �            1259    16451    user_account    TABLE     �   CREATE TABLE public.user_account (
    id integer NOT NULL,
    username text,
    password text,
    token text,
    email text,
    phone text,
    type integer
);
     DROP TABLE public.user_account;
       public         heap    postgres    false    4            �            1259    16457    user_account_id_seq    SEQUENCE     �   ALTER TABLE public.user_account ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    214            �          0    16394    account 
   TABLE DATA           O   COPY public.account (id, username, password, birth_of_date, token) FROM stdin;
    public          postgres    false    201   �3       �          0    16402    artist 
   TABLE DATA           V   COPY public.artist (id, name, family, "nickName", "bornCity", "bornDate") FROM stdin;
    public          postgres    false    203   4       �          0    16408    artist_album 
   TABLE DATA           L   COPY public.artist_album (id, artist_id, album_name, cover_img) FROM stdin;
    public          postgres    false    204   Y4       �          0    16418    artist_song 
   TABLE DATA           C   COPY public.artist_song (id, album_id, url, song_name) FROM stdin;
    public          postgres    false    206   L5       �          0    16426 	   downloads 
   TABLE DATA           H   COPY public.downloads (account_id, game_id, download_count) FROM stdin;
    public          postgres    false    208   ^8       �          0    24578    favorite_song 
   TABLE DATA           =   COPY public.favorite_song (id, user_id, song_id) FROM stdin;
    public          postgres    false    217   �8       �          0    16432    game 
   TABLE DATA           ~   COPY public.game (id, title, developer, genre, price, release_year, controller_support, reviews, size, file_path) FROM stdin;
    public          postgres    false    209   �8       �          0    16438 	   play_list 
   TABLE DATA           6   COPY public.play_list (id, user_id, name) FROM stdin;
    public          postgres    false    210   ;       �          0    16446    play_list_item 
   TABLE DATA           J   COPY public.play_list_item (id, play_list_id, artist_song_id) FROM stdin;
    public          postgres    false    212   !;       �          0    16451    user_account 
   TABLE DATA           Y   COPY public.user_account (id, username, password, token, email, phone, type) FROM stdin;
    public          postgres    false    214   >;                  0    0    account_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.account_id_seq', 1, true);
          public          postgres    false    202                       0    0    artist_album_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.artist_album_id_seq', 22, true);
          public          postgres    false    205                       0    0    artist_song_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.artist_song_id_seq', 136, true);
          public          postgres    false    207            	           0    0    favorite_song_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.favorite_song_id_seq', 2, true);
          public          postgres    false    216            
           0    0    play_list_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.play_list_id_seq', 1, false);
          public          postgres    false    211                       0    0    play_list_item_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.play_list_item_id_seq', 1, false);
          public          postgres    false    213                       0    0    user_account_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.user_account_id_seq', 15, true);
          public          postgres    false    215            \           2606    16460    account account_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.account DROP CONSTRAINT account_pkey;
       public            postgres    false    201            `           2606    16462    artist_album artist_album_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.artist_album
    ADD CONSTRAINT artist_album_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.artist_album DROP CONSTRAINT artist_album_pkey;
       public            postgres    false    204            ^           2606    16464    artist artist_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.artist
    ADD CONSTRAINT artist_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.artist DROP CONSTRAINT artist_pkey;
       public            postgres    false    203            b           2606    16466    artist_song artist_song_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.artist_song
    ADD CONSTRAINT artist_song_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.artist_song DROP CONSTRAINT artist_song_pkey;
       public            postgres    false    206            d           2606    16468    game game_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.game
    ADD CONSTRAINT game_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.game DROP CONSTRAINT game_pkey;
       public            postgres    false    209            h           2606    16470 "   play_list_item play_list_item_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.play_list_item
    ADD CONSTRAINT play_list_item_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.play_list_item DROP CONSTRAINT play_list_item_pkey;
       public            postgres    false    212            f           2606    16472    play_list play_list_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.play_list
    ADD CONSTRAINT play_list_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.play_list DROP CONSTRAINT play_list_pkey;
       public            postgres    false    210            j           2606    16474    user_account user_account_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.user_account
    ADD CONSTRAINT user_account_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.user_account DROP CONSTRAINT user_account_pkey;
       public            postgres    false    214            �   _   x��;�  ��ò_�Xx
�eFH��<���OHX�(e���T�*{f�I�0J�f'��D\��K��t
P����3�wn{<��S��      �   8   x�3�tMʄ�Ԍ��<NCKc.#NǢ�4$!i�e��X���B���b���� ���      �   �   x���?�0��|-���`4��x`1%�K���[z�76׼��K����y[Ν/���(�������L��3��}kz��m�mW�f�	��v�੧a��� �dЌ_/#��g��Ȑ8��#��;1:�8	���	%r�k��M�b*�M�m��P�mn�����6Q:S�mn�TD�^��=	E+;Xl� ��R��b���%�0H-u�ئA����y@?7�      �     x����jAF��̻�tW�5/�2ɲ7�(A!�����i4�_�ӫBH|hs�K�mm��i�/OO߯O����s����?��^/�ϗ��|}�e~;Ư��1�����_O��ϧ�1B||y������m�	���oc� ��6����oc� \�6����oc� ��6�@�r?��5�Dei$:(K$1��T3(K&���t+(K(�����AYJ�����b���3��X�IZj�DҒ�e��^���c���k$���m^_�܀��d<��$�FҒ�;IK2�HZ��LҒ���d���$�F[�L'i}j	$-ɤHҒL2��d���$�IK2)��$�
IK2i��k�I��5�N���HZ�ɑ�%�l$-�d'iI&'��dr&iI&�Ӛdr%iI&7��d:I���@ҒL�$-�#iI�8IK2%��$Sf�yM2���$S*IK2���&�IZ_*��dj$iI�IK2�IZ���Ʋ&��IZ����%�ZIZ����5�NҺ�	$-ɴHҒL3��dڬ��I�%��dZ&iI���dZ%iI�5��d:I��2��$�#IK2}ָh�ߝ�%��HZ�除%�^HZ�镤%��HZ��$}����[�0{\��Fں�Nں���u�2i��?�� ���������}wb&l�� @�����h���D'mm'&��v�/hk;����y3��μ��];}�E'��Ӡ��L��3��v�I[۱D�ڎe��v����c����].:����i�@�ڎG��v�H[�q'mm�ik;�I[��B���x���?M]��      �      x�3�4���06�4����� �h      �      x�3�4�4�2�1z\\\ �      �   I  x����r�0���Shו=���I���3m8��Ab$A�>}�m�ˎ3��2�$��:��$��^��^���)�Z;4@��1$,���h�*���>����ZY��[E���skJ�[!�b�h4���ޔh9�3eөzE)�I��6�|E��N�)��mנC����Z�C��Ȧp[�֢��{��<��0��,�����3�BNc�p���PTğ`ۍ$��A�'�!�E�7�]�4��<�4�KS'ޅ�',�VV�Ud?Ȇ|C���+uEo9���m ��IG!��..�T�0�Y�˶o��������/�M����u׈�x�]��� ZX6k&Y� af�/���h����/j�xf[Q OG$_�+�hHt�O��<���vG>��'76���k��@�/�p�3
#���WԺ�W'��<KG��+�-����*�?����Y2�X�|]�tK��P���%���~#�������YZ߄�^����X����Ɖgyv��8$���Mc��R��F��谔�����m%�D�K���:�4�� I^�3{��Y�V? 41%R      �      x������ � �      �      x������ � �      �   �   x��ϱ�0E�9��*�c;٘`cb쒦IqE���*FF��|�.��wU5sa	�a*�P+d���9�D�K�0�)J�%����3^��C�@b�e�箏��s\z��P�}�+�U�����\ϧ����ۺ|�������L�     