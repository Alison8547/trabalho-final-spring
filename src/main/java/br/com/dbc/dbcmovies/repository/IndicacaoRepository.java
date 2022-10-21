package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class IndicacaoRepository {

    private ConexaoBancoDeDados conexao;

    public IndicacaoRepository(ConexaoBancoDeDados conexao) {
        this.conexao = conexao;
    }

    public boolean indicar(Integer idUsuario, String nomeItem) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexao.getConnection();

            String sql = "INSERT INTO INDICACAO\n" +
                    "(id_usuario, nome_item)\n" +
                    "VALUES(?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, idUsuario);
            stmt.setString(2, nomeItem);

            // Executa-se a consulta
            int res = stmt.executeUpdate();

            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
