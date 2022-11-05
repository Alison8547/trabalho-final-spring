package br.com.dbc.dbcmovies.repository;

//import org.springframework.stereotype.Repository;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//@Repository
//public class IndicacaoRepository {
//
//    private ConexaoBancoDeDados conexao;
//
//    public IndicacaoRepository(ConexaoBancoDeDados conexao) {
//        this.conexao = conexao;
//    }
//
//    public Indicacao indicar(String nomeItem, Integer idUsuario) throws BancoDeDadosException {
//        Connection con = null;
//        try {
//            con = conexao.getConnection();
//
//            String sql = "INSERT INTO INDICACAO\n" +
//                    "(id_usuario, nome_item)\n" +
//                    "VALUES(?, ?)";
//
//            PreparedStatement stmt = con.prepareStatement(sql);
//
//            stmt.setInt(1, idUsuario);
//            stmt.setString(2, nomeItem);
//
//            // Executa-se a consulta
//            stmt.executeUpdate();
//
//            return new Indicacao(nomeItem, idUsuario);
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
