package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.entity.Avaliacao;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoRepository {

    private ConexaoBancoDeDados conexao;
    private UsuarioRepository usuarioRepository;
    private ItemRepository itemRepository;

    public AvaliacaoRepository(ConexaoBancoDeDados conexao, UsuarioRepository usuarioRepository, ItemRepository itemRepository) {
        this.conexao = conexao;
        this.usuarioRepository = usuarioRepository;
        this.itemRepository = itemRepository;
    }

    public Avaliacao adicionar(Avaliacao avaliacao, Integer usuarioId, Integer itemId) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexao.getConnection();

            String sql = "INSERT INTO AVALIACAO\n" +
                    "(id_usuario, id_item_entretenimento, nota, comentario)\n" +
                    "VALUES(?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, itemId);
            stmt.setDouble(3, avaliacao.getNota());
            stmt.setString(4, avaliacao.getComentario());

            int res = stmt.executeUpdate();
            return avaliacao;
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

    public List<Avaliacao> listarAvaliacoes () throws BancoDeDadosException {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        Connection con = null;

        try{
            con = conexao.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM AVALIACAO a ");
            sql.append("INNER JOIN ITEM_ENTRETENIMENTO ie ON (ie.id_item_entretenimento = a.id_item_entretenimento) ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            ResultSet res = stmt.executeQuery();

            while (res.next()){
                Avaliacao aval = new Avaliacao();
                aval.setUsuario(usuarioRepository.pegar(res.getInt("id_usuario")));
                aval.setItemEntretenimento(itemRepository.pegar(res.getInt("id_item_entretenimento")));
                aval.setNota(res.getDouble("nota"));
                aval.setComentario(res.getString("comentario"));

                avaliacoes.add(aval);
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
        return avaliacoes;
    }


    public List<Avaliacao> listarAvaliacoesUsuario (Integer idUsuario) throws BancoDeDadosException {

        List<Avaliacao> avaliacoes = new ArrayList<>();
        Connection con = null;

        try{
            con = conexao.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM AVALIACAO a ");
            sql.append("INNER JOIN ITEM_ENTRETENIMENTO ie ON (ie.id_item_entretenimento = a.id_item_entretenimento) ");
            sql.append("WHERE id_usuario = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, idUsuario);

            ResultSet res = stmt.executeQuery();

            while (res.next()){
                Avaliacao aval = new Avaliacao();
                aval.setUsuario(usuarioRepository.pegar(res.getInt("id_usuario")));
                aval.setItemEntretenimento(itemRepository.pegar(res.getInt("id_item_entretenimento")));
                aval.setNota(res.getDouble("nota"));
                aval.setComentario(res.getString("comentario"));

                avaliacoes.add(aval);
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
        return avaliacoes;
    }

    public boolean remover(Integer idUsuario, Integer idItem) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexao.getConnection();

            String sql = "DELETE FROM AVALIACAO WHERE id_usuario = ? AND id_item_entretenimento = ? ";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idItem);

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

    public boolean editar(Avaliacao avaliacao, Integer idUsuario, Integer idItem) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexao.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE AVALIACAO SET \n");

            if (avaliacao.getNota() != null) {
                sql.append("nota = ? ,");
            }
            if (avaliacao.getComentario() != null) {
                sql.append("comentario = ? ,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE id_usuario = ? AND id_item_entretenimento = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;

            if (avaliacao.getNota() != null) {
                stmt.setDouble(index++, avaliacao.getNota());
            }
            if (avaliacao.getComentario() != null) {
                stmt.setString(index++, avaliacao.getComentario());
            }

            stmt.setInt(index++, idUsuario);
            stmt.setInt(index, idItem);

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
