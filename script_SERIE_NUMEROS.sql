-- Ejecuta este script desde SQL Developer o SQL*Plus, conectado con el
-- usuario que utilizará el programa:
CREATE TABLE SERIE_NUMEROS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NUMERO NUMBER NOT NULL,
    FECHA_REGISTRO DATE DEFAULT SYSDATE
);

COMMIT;