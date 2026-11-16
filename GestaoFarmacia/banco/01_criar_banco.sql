-- Execute conectado ao banco padrão "postgres":
--   psql -U postgres -f banco/01_criar_banco.sql
CREATE DATABASE gestaofarmacia
    WITH ENCODING = 'UTF8'
    TEMPLATE = template0;
