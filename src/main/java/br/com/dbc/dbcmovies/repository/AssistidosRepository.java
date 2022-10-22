package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.entity.ItemEntretenimento;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.repository.interfaces.Interacao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AssistidosRepository implements Interacao {

    private ConexaoBancoDeDados conexao;
    public AssistidosRepository(ConexaoBancoDeDados conexao) {
        this.conexao = conexao;
    }

    public List<ItemEntretenimento> listarAssistidos (Integer idUsuario) throws BancoDeDadosException {

        List<ItemEntretenimento> itemEntretenimentos = new ArrayList<>();
        Connection con = null;

        try{
            con = conexao.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM ASSISTIDOS a ");
            sql.append("INNER JOIN ITEM_ENTRETENIMENTO ie ON (ie.id_item_entretenimento = a.id_item_entretenimento) ");
            sql.append("WHERE id_usuario = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, idUsuario);

            ResultSet res = stmt.executeQuery();

            while (res.next()){
                ItemEntretenimento item = new ItemEntretenimento();
                item.setId(res.getInt("id_item_entretenimento"));
                item.setNome(res.getString("nome"));
                item.setTipo(res.getString("tipo"));
                item.setGenero(res.getString("genero"));
                item.setSinopse(res.getString("sinopse"));
                item.setAnoLancamento(res.getString("ano_lancamento"));
                item.setClassificacao(res.getInt("classificacao"));
                item.setPlataforma(res.getString("plataforma"));
                item.setDuracao(res.getString("duracao"));
                item.setTemporadas(res.getInt("temporadas"));
                item.setEpisodios(res.getInt("episodios"));
         //       item.setMediaAvaliacoes(res.getDouble("mediaAvaliacoes"));

                itemEntretenimentos.add(item);
            }

        }catch (
                SQLException ex){
            throw new BancoDeDadosException(ex.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return itemEntretenimentos;
    }


    public boolean deletarAssistido(Integer idItem, Integer idUsuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexao.getConnection();

            String sql = "DELETE FROM ASSISTIDOS WHERE id_item_entretenimento = ? AND id_usuario = ? ";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, idItem);
            stmt.setInt(2, idUsuario);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerItemPorId.res=" + res);

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


    @Override
    public boolean marcarAssistido(Integer idUsuario, Integer idItem) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexao.getConnection();

            String sql = "INSERT INTO ASSISTIDOS\n" +
                    "(id_usuario,id_item_entretenimento)\n" +
                    "VALUES(?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idItem);

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

    @Override
    public boolean incluirIndicacao(String nomeItem, Integer idUsuario) throws BancoDeDadosException {
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
