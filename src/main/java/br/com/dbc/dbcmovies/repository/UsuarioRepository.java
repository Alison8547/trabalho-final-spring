package br.com.dbc.dbcmovies.repository;


import br.com.dbc.dbcmovies.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Integer> {

    UsuarioEntity findByEmailAndSenha(String email,String senha);

//    private ConexaoBancoDeDados conexao;
//
//    public UsuarioRepository(ConexaoBancoDeDados conexao) {
//        this.conexao = conexao;
//    }
//
//    @Override
//    public Integer getProximoId(Connection connection) throws SQLException {
//        String sql = "SELECT SEQ_USUARIO.nextval mysequence from DUAL";
//
//        Statement stmt = connection.createStatement();
//        ResultSet resultSet = stmt.executeQuery(sql);
//
//        if (resultSet.next()) {
//            return resultSet.getInt("mysequence");
//        }
//
//        return null;
//    }
//
//    @Override
//    public UsuarioEntity adicionar(UsuarioEntity usuarioEntity) throws RegraDeNegocioException {
//        Connection conn = null;
//
//        try {
//            conn = conexao.getConnection();
//            Integer proximoId = this.getProximoId(conn);
//            usuarioEntity.setId(proximoId);
//
//            String sql = "INSERT INTO USUARIO\n" +
//                    "(id_usuario, nome, idade, email, senha, tipo_usuario)\n" +
//                    "VALUES(?, ?, ?, ?, ?, ?)\n";
//
//            PreparedStatement stmt = conn.prepareStatement(sql);
//
//            stmt.setInt(1, usuarioEntity.getId());
//            stmt.setString(2, usuarioEntity.getNome());
//            stmt.setInt(3, usuarioEntity.getIdade());
//            stmt.setString(4, usuarioEntity.getEmail());
//            stmt.setString(5, usuarioEntity.getSenha());
//            stmt.setString(6, usuarioEntity.getTipoUsuario().getDescricao());
//
//            int res = stmt.executeUpdate();
//
//            return usuarioEntity;
//
//        } catch (SQLException e) {
//            throw new RegraDeNegocioException(e.getMessage());
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.getCause();
//            }
//        }
//    }
//
//    @Override
//    public boolean remover(Integer id) throws RegraDeNegocioException {
//        Connection conn = null;
//
//        try {
//            conn = conexao.getConnection();
//            String sql = "DELETE FROM USUARIO WHERE id_usuario = ?";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, id);
//            int res = stmt.executeUpdate();
//            log.info("Usuário deletado com sucesso");
//            return res > 0;
//
//        } catch (SQLException e) {
//            throw new RegraDeNegocioException(e.getMessage());
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public boolean editar(Integer id, UsuarioEntity usuarioEntity) throws RegraDeNegocioException {
//        Connection conn = null;
//        try {
//            conn = conexao.getConnection();
//            StringBuilder sql = new StringBuilder();
//
//            sql.append("UPDATE USUARIO SET ");
//            sql.append(" nome = ?,");
//            sql.append(" idade = ?,");
//            sql.append(" email = ?,");
//            sql.append(" senha = ?");
//            sql.append(" WHERE id_usuario = ? ");
//
//            PreparedStatement stmt = conn.prepareStatement(sql.toString());
//
//            stmt.setString(1, usuarioEntity.getNome());
//            stmt.setInt(2, usuarioEntity.getIdade());
//            stmt.setString(3, usuarioEntity.getEmail());
//            stmt.setString(4, usuarioEntity.getSenha());
//
//            stmt.setInt(5, id);
//
//            int res = stmt.executeUpdate();
//
//            return res > 0;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public boolean tornarUsuarioAdmin(Integer id) throws RegraDeNegocioException {
//        Connection conn = null;
//        try {
//            conn = conexao.getConnection();
//
//            StringBuilder sql = new StringBuilder();
//
//            sql.append("UPDATE USUARIO SET tipo_usuario = ? ");
//            sql.append(" WHERE id_usuario = ? ");
//
//            PreparedStatement stmt = conn.prepareStatement(sql.toString());
//
//            stmt.setString(1, "administrador");
//            stmt.setInt(2, id);
//
//            int res = stmt.executeUpdate();
//
//            return res > 0;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public List<UsuarioEntity> listar() throws RegraDeNegocioException {
//        List<UsuarioEntity> usuarioEntityList = new ArrayList<>();
//
//        Connection conn = null;
//
//        try {
//            conn = conexao.getConnection();
//            Statement stmt = conn.createStatement();
//
//            String sql = "SELECT * FROM USUARIO";
//
//            ResultSet res = stmt.executeQuery(sql);
//
//            while (res.next()) {
//                UsuarioEntity usuarioEntity = new UsuarioEntity();
//                usuarioEntity.setId(res.getInt("id_usuario"));
//                usuarioEntity.setNome(res.getString("nome"));
//                usuarioEntity.setIdade(res.getInt("idade"));
//                usuarioEntity.setEmail(res.getString("email"));
//                usuarioEntity.setSenha(res.getString("senha"));
//                if (res.getString("tipo_usuario").equalsIgnoreCase("administrador")) {
//                    usuarioEntity.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
//
//                }else {
//                    usuarioEntity.setTipoUsuario(TipoUsuario.CLIENTE);
//                }
//               usuarioEntityList.add(usuarioEntity);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return usuarioEntityList;
//    }
//
//
//    public UsuarioEntity pegar(Integer id) throws RegraDeNegocioException {
//        Connection conn = null;
//        UsuarioEntity usuarioEntity = new UsuarioEntity();
//
//        try {
//            conn = conexao.getConnection();
//            String sql = "SELECT * FROM USUARIO WHERE id_usuario = ?";
//
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, id);
//
//            ResultSet res = stmt.executeQuery();
//
//            while (res.next()){
//                usuarioEntity.setId(res.getInt("id_usuario"));
//                usuarioEntity.setNome(res.getString("nome"));
//                usuarioEntity.setIdade(res.getInt("idade"));
//                usuarioEntity.setEmail(res.getString("email"));
//                usuarioEntity.setSenha(res.getString("senha"));
//                if (res.getString("tipo_usuario").equalsIgnoreCase("administrador")) {
//                    usuarioEntity.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
//
//                }else {
//                    usuarioEntity.setTipoUsuario(TipoUsuario.CLIENTE);
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new RegraDeNegocioException(e.getMessage());
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return usuarioEntity;
//    }
//
//    public UsuarioEntity pegarLogin(UsuarioEntity usuarioEntityLogin) throws RegraDeNegocioException {
//        Connection conn = null;
//        UsuarioEntity usuarioEntity = new UsuarioEntity();
//
//        try {
//            conn = conexao.getConnection();
//            String sql = "SELECT * FROM USUARIO WHERE email = ? AND senha = ?";
//
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, usuarioEntityLogin.getEmail());
//            stmt.setString(2, usuarioEntityLogin.getSenha());
//
//            ResultSet res = stmt.executeQuery();
//
//            while (res.next()){
//                usuarioEntity.setId(res.getInt("id_usuario"));
//                usuarioEntity.setNome(res.getString("nome"));
//                usuarioEntity.setIdade(res.getInt("idade"));
//                usuarioEntity.setEmail(res.getString("email"));
//                usuarioEntity.setSenha(res.getString("senha"));
//                if (res.getString("tipo_usuario").equalsIgnoreCase("administrador")) {
//                    usuarioEntity.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
//
//                }else {
//                    usuarioEntity.setTipoUsuario(TipoUsuario.CLIENTE);
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new RegraDeNegocioException(e.getMessage());
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return usuarioEntity;
//    }
}
