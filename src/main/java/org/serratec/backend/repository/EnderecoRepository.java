package org.serratec.backend.repository;

import org.serratec.backend.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

    // VERIFICA SE O ENDEREÇO FOI ENSERIDO CORRETAMENTE
    Endereco findByCepAndLogradouro(String cep, String logradouro);


}
