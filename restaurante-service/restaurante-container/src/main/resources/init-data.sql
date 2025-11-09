INSERT INTO restaurante.restaurantes(id, nome, ativo)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb45', 'restaurante_1', TRUE);
INSERT INTO restaurante.restaurantes(id, nome, ativo)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb46', 'restaurante_2', FALSE);

INSERT INTO restaurante.produtos(id, nome, preco, disponivel)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb47', 'produto_1', 25.00, FALSE);
INSERT INTO restaurante.produtos(id, nome, preco, disponivel)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb48', 'produto_2', 50.00, TRUE);
INSERT INTO restaurante.produtos(id, nome, preco, disponivel)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb49', 'produto_3', 20.00, FALSE);
INSERT INTO restaurante.produtos(id, nome, preco, disponivel)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb50', 'produto_4', 40.00, TRUE);

INSERT INTO restaurante.restaurante_produtos(id, restaurante_id, produto_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb51', 'd215b5f8-0249-4dc5-89a3-51fd148cfb45', 'd215b5f8-0249-4dc5-89a3-51fd148cfb47');
INSERT INTO restaurante.restaurante_produtos(id, restaurante_id, produto_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb52', 'd215b5f8-0249-4dc5-89a3-51fd148cfb45', 'd215b5f8-0249-4dc5-89a3-51fd148cfb48');
INSERT INTO restaurante.restaurante_produtos(id, restaurante_id, produto_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb53', 'd215b5f8-0249-4dc5-89a3-51fd148cfb46', 'd215b5f8-0249-4dc5-89a3-51fd148cfb49');
INSERT INTO restaurante.restaurante_produtos(id, restaurante_id, produto_id)
	VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb54', 'd215b5f8-0249-4dc5-89a3-51fd148cfb46', 'd215b5f8-0249-4dc5-89a3-51fd148cfb50');
