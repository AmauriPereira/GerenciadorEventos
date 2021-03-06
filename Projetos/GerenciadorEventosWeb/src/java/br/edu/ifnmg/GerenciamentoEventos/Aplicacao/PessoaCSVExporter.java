/*
 *   This file is part of SGEA - Sistema de Gestão de Eventos Acadêmicos - TADS IFNMG Campus Januária.
 *
 *   SGEA is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   SGEA is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with SGEA.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.edu.ifnmg.GerenciamentoEventos.Aplicacao;

import br.edu.ifnmg.DomainModel.Pessoa;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author petronio
 */
public class PessoaCSVExporter extends CSVExporter<Pessoa> {

    DateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm");
    
    @Override
    protected StringBuilder gerarLinha(Pessoa obj) {
        return new StringBuilder(limparTexto(obj.getNome())).append(";")
                .append(obj.getCpf() != null ? limparTexto(obj.getCpf()) : "").append(";")
                .append(obj.getTelefone() != null ? limparTexto(obj.getTelefone()): "").append(";")
                .append(obj.getEmail() != null ? limparTexto(obj.getEmail()) : "").append(";")
                .append(obj.getDataNascimento() != null ? df.format(obj.getDataNascimento()) : "").append(";")
                .append(obj.getPerfil() != null ? limparTexto(obj.getPerfil().getId().toString()) : "").append(";")
                .append(obj.getTipo() != null ? limparTexto(obj.getTipo().name()) : "").append(";");
    }

    @Override
    protected StringBuilder gerarCabecalho(Pessoa obj) {
        return new StringBuilder("nome;cpf;telefone;email;dataNascimento;perfil;tipo;");
        
    }
    
}
