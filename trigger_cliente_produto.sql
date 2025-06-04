CREATE TABLE log_operacoes (
    id_log SERIAL PRIMARY KEY,  
    tabela_afetada VARCHAR(100) NOT NULL,
    id_registro_afetado VARCHAR(255),      
    tipo_operacao VARCHAR(10) NOT NULL,    
    dados_antigos TEXT,                    
    dados_novos TEXT,                     
    usuario_operacao VARCHAR(100),         
    data_hora_operacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);

CREATE OR REPLACE FUNCTION log_operacao_cliente()
RETURNS TRIGGER AS $$
DECLARE
    v_dados_antigos TEXT;
    v_dados_novos TEXT;
    v_id_registro_afetado VARCHAR(255);
    v_usuario_record RECORD; 
BEGIN
    IF TG_OP = 'DELETE' THEN
        v_id_registro_afetado := OLD.id::text;

        
        SELECT cpf, email, nome, status, telefone, id_perfil
        INTO v_usuario_record
        FROM usuario
        WHERE id = OLD.id;

        v_dados_antigos := jsonb_build_object(
            'id', OLD.id,
            'cpf', v_usuario_record.cpf,
            'email', v_usuario_record.email,
            'nome', v_usuario_record.nome,
            'status', v_usuario_record.status,
            'telefone', v_usuario_record.telefone,
            'id_perfil', v_usuario_record.id_perfil,
            'complemento', OLD.complemento,
            'id_endereco', OLD.id_endereco
        )::text;

        INSERT INTO log_operacoes (
            tabela_afetada, id_registro_afetado, tipo_operacao,
            dados_antigos, usuario_operacao
        ) VALUES (
            'cliente', v_id_registro_afetado, 'DELETE',
            v_dados_antigos, COALESCE(current_setting('app.current_user', true), CURRENT_USER)
        );

    ELSIF TG_OP = 'INSERT' THEN
        v_id_registro_afetado := NEW.id::text;

        
        SELECT cpf, email, nome, status, telefone, id_perfil
        INTO v_usuario_record
        FROM usuario
        WHERE id = NEW.id; 

        v_dados_novos := jsonb_build_object(
            'id', NEW.id,
            'cpf', v_usuario_record.cpf,
            'email', v_usuario_record.email,
            'nome', v_usuario_record.nome,
            'status', v_usuario_record.status,
            'telefone', v_usuario_record.telefone,
            'id_perfil', v_usuario_record.id_perfil,
            'complemento', NEW.complemento,
            'id_endereco', NEW.id_endereco
        )::text;

        INSERT INTO log_operacoes (
            tabela_afetada, id_registro_afetado, tipo_operacao,
            dados_novos, usuario_operacao
        ) VALUES (
            'cliente', v_id_registro_afetado, 'INSERT',
            v_dados_novos, COALESCE(current_setting('app.current_user', true), CURRENT_USER)
        );

    ELSIF TG_OP = 'UPDATE' THEN
        v_id_registro_afetado := NEW.id::text;

        
        SELECT cpf, email, nome, status, telefone, id_perfil
        INTO v_usuario_record
        FROM usuario
        WHERE id = OLD.id;
        v_dados_antigos := jsonb_build_object(
            'id', OLD.id,
            'cpf', v_usuario_record.cpf,
            'email', v_usuario_record.email,
            'nome', v_usuario_record.nome,
            'status', v_usuario_record.status,
            'telefone', v_usuario_record.telefone,
            'id_perfil', v_usuario_record.id_perfil,
            'complemento', OLD.complemento,
            'id_endereco', OLD.id_endereco
        )::text;

        
        SELECT cpf, email, nome, status, telefone, id_perfil
        INTO v_usuario_record
        FROM usuario
        WHERE id = NEW.id; 
        v_dados_novos := jsonb_build_object(
            'id', NEW.id,
            'cpf', v_usuario_record.cpf,
            'email', v_usuario_record.email,
            'nome', v_usuario_record.nome,
            'status', v_usuario_record.status,
            'telefone', v_usuario_record.telefone,
            'id_perfil', v_usuario_record.id_perfil,
            'complemento', NEW.complemento,
            'id_endereco', NEW.id_endereco
        )::text;

        INSERT INTO log_operacoes (
            tabela_afetada, id_registro_afetado, tipo_operacao,
            dados_antigos, dados_novos, usuario_operacao
        ) VALUES (
            'cliente', v_id_registro_afetado, 'UPDATE',
            v_dados_antigos, v_dados_novos, COALESCE(current_setting('app.current_user', true), CURRENT_USER)
        );
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_log_cliente
AFTER INSERT OR UPDATE OR DELETE ON cliente
FOR EACH ROW
EXECUTE FUNCTION log_operacao_cliente();



CREATE OR REPLACE FUNCTION log_operacao_produto()
RETURNS TRIGGER AS $$
DECLARE
    v_dados_antigos TEXT;
    v_dados_novos TEXT;
    v_id_registro_afetado VARCHAR(255);
BEGIN
    IF TG_OP = 'DELETE' THEN
        v_id_registro_afetado := OLD.id::text;
        v_dados_antigos := row_to_json(OLD)::text;

        INSERT INTO log_operacoes (
            tabela_afetada, id_registro_afetado, tipo_operacao,
            dados_antigos, usuario_operacao
        ) VALUES (
            'produto', v_id_registro_afetado, 'DELETE',
            v_dados_antigos, COALESCE(current_setting('app.current_user', true), CURRENT_USER)
        );

    ELSIF TG_OP = 'INSERT' THEN
        v_id_registro_afetado := NEW.id::text;
        v_dados_novos := row_to_json(NEW)::text;

        INSERT INTO log_operacoes (
            tabela_afetada, id_registro_afetado, tipo_operacao,
            dados_novos, usuario_operacao
        ) VALUES (
            'produto', v_id_registro_afetado, 'INSERT',
            v_dados_novos, COALESCE(current_setting('app.current_user', true), CURRENT_USER)
        );

    ELSIF TG_OP = 'UPDATE' THEN
        v_id_registro_afetado := NEW.id::text;
        v_dados_antigos := row_to_json(OLD)::text;
        v_dados_novos := row_to_json(NEW)::text;

        INSERT INTO log_operacoes (
            tabela_afetada, id_registro_afetado, tipo_operacao,
            dados_antigos, dados_novos, usuario_operacao
        ) VALUES (
            'produto', v_id_registro_afetado, 'UPDATE',
            v_dados_antigos, v_dados_novos, COALESCE(current_setting('app.current_user', true), CURRENT_USER)
        );
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_log_produto
AFTER INSERT OR UPDATE OR DELETE ON produto
FOR EACH ROW 
EXECUTE FUNCTION log_operacao_produto();





