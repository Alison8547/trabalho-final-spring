package br.com.dbc.dbcmovies.repository;

import br.com.dbc.dbcmovies.entity.Filtro;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.repository.interfaces.Repositorio;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepository implements Repositorio<Integer, ItemEntretenimentoEntity> {

    private ConexaoBancoDeDados conexao;

    public ItemRepository(ConexaoBancoDeDados conexao) {
        this.conexao = conexao;
    }

    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_ITEM_ENTRETENIMENTO.nextval mysequence from DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getInt("mysequence");
            }

            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    @Override
    public ItemEntretenimentoEntity adicionar(ItemEntretenimentoEntity item) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexao.getConnection();

            Integer proximoId = this.getProximoId(con);
            item.setId(proximoId);

            String sql = "INSERT INTO ITEM_ENTRETENIMENTO\n" +
                    "(id_item_entretenimento , nome, tipo, genero, sinopse, ano_lancamento, classificacao, plataforma, duracao, temporadas, episodios)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, item.getId());
            stmt.setString(2, item.getNome());
            stmt.setString(3, item.getTipo());
            stmt.setString(4, item.getGenero());
            stmt.setString(5, item.getSinopse());
            stmt.setString(6, item.getAnoLancamento());
            stmt.setInt(7, item.getClassificacao());
            stmt.setString(8, item.getPlataforma());
            stmt.setString(9, item.getDuracao());
            stmt.setInt(10, item.getTemporadas());
            stmt.setInt(11, item.getEpisodios());

            int res = stmt.executeUpdate();

            return item;

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
    public List<ItemEntretenimentoEntity> listar() throws BancoDeDadosException {
        List<ItemEntretenimentoEntity> itemEntretenimentoEntities = new ArrayList<>();
        Connection con = null;

        try{
            con = conexao.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM ITEM_ENTRETENIMENTO";
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()){
                ItemEntretenimentoEntity item = new ItemEntretenimentoEntity();
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

                itemEntretenimentoEntities.add(item);
            }

        }catch (SQLException ex){
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
        return itemEntretenimentoEntities;
    }

    @Override
    public boolean editar(Integer id, ItemEntretenimentoEntity item) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexao.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ITEM_ENTRETENIMENTO SET \n");

            if (item.getNome() != null){
                sql.append("nome = ? ,");
            }
            if (item.getGenero() != null) {
                sql.append("genero = ?,");
            }
            if (item.getSinopse() != null) {
                sql.append("sinopse = ? ,");
            }
            if (item.getAnoLancamento() != null) {
                sql.append("ano_lancamento = ? ,");
            }
            if (item.getClassificacao() != null) {
                sql.append("classificacao = ? ,");
            }
            if (item.getPlataforma() != null) {
                sql.append("plataforma = ? ,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE id_item_entretenimento = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;

            if (item.getNome() != null){
                stmt.setString(index++, item.getNome());
            }
            if (item.getGenero() != null) {
                stmt.setString(index++, item.getGenero());
            }
            if (item.getSinopse() != null) {
                stmt.setString(index++, item.getSinopse());
            }
            if (item.getAnoLancamento() != null) {
                stmt.setString(index++, item.getAnoLancamento());
            }
            if (item.getClassificacao() != null) {
                stmt.setInt(index++, item.getClassificacao());
            }
            if (item.getPlataforma() != null) {
                stmt.setString(index++, item.getPlataforma());
            }

            stmt.setInt(index, id);

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

    public List<ItemEntretenimentoEntity> filtrarItens(Filtro filtro) throws BancoDeDadosException {
        List<ItemEntretenimentoEntity> itemEntretenimentoEntities = new ArrayList<>();
        Connection con = null;

        try{
            con = conexao.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM ITEM_ENTRETENIMENTO ");
            sql.append("WHERE UPPER(tipo) = ? AND UPPER(genero) = ? AND classificacao <= ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, filtro.getTipo().toUpperCase());
            stmt.setString(2, filtro.getGenero().toUpperCase());
            stmt.setInt(3, filtro.getClassificacao());

            ResultSet res = stmt.executeQuery();

            while (res.next()){
                ItemEntretenimentoEntity item = new ItemEntretenimentoEntity();
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

                itemEntretenimentoEntities.add(item);
            }

        }catch (SQLException ex){
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
        return itemEntretenimentoEntities;
    }

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexao.getConnection();

            String sql = "DELETE FROM ITEM_ENTRETENIMENTO WHERE id_item_entretenimento = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

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

    public Double calcularAvaliacoes(Integer id) throws BancoDeDadosException{
        Double media = null;
        Connection con = null;

        try {
            con = conexao.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT AVG(nota) AS media FROM AVALIACAO \n");
            sql.append("WHERE id_item_entretenimento = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();

            res.next();
            media = res.getDouble("media");

        }catch (SQLException ex){
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
        return media;
    }

    public ItemEntretenimentoEntity pegar(Integer id) throws BancoDeDadosException {
        Connection conn = null;
        ItemEntretenimentoEntity item = new ItemEntretenimentoEntity();

        try {
            conn = conexao.getConnection();
            String sql = "SELECT * FROM ITEM_ENTRETENIMENTO WHERE id_item_entretenimento = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();

            while (res.next()){
                item.setId(res.getInt("id_item_entretenimento"));
                item.setNome(res.getString("nome"));
                item.setTipo(res.getString("tipo"));
                item.setGenero(res.getString("genero"));
                item.setSinopse(res.getString("sinopse"));
                item.setAnoLancamento(res.getString("ano_lancamento"));
                item.setClassificacao(res.getInt("classificacao"));
                item.setPlataforma(res.getString("plataforma"));

            }

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return item;
    }
}
