
package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DAOCidade {
    
    public List<Cidade> getLista(){     
        String sql = "select * from cidade";
        List<Cidade> listaCidade = new ArrayList<>();
        try{
            PreparedStatement pst = Conexao.getPreparedStatement(sql);
            ResultSet rs = pst.executeQuery();
        while(rs.next()){
            Cidade objCidade = new Cidade();
            objCidade.setCodigo(rs.getInt("codigo"));
            objCidade.setNome(rs.getString("nome"));
            objCidade.setUf(rs.getString("uf"));
            listaCidade.add(objCidade);
        }
        }catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro de SQL"+erro.getMessage());
        }            
        return listaCidade;
        
    }
    
    public boolean salvar (Cidade obj){
        if(obj.getCodigo()==null){
            return incluir(obj);            
        }else{
            return alterar(obj);
            
        }               

    }
    
    
    public boolean incluir(Cidade obj){
        String sql = "insert into cidade (nome, uf) values(?,?)";
        try{
            PreparedStatement pst = Conexao.getPreparedStatement(sql);
            pst.setString(1, obj.getNome());
            pst.setString(1, obj.getUf());
            if(pst.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Cidade cadastrada com sucesso");
            return true;
            }else {
                JOptionPane.showMessageDialog(null, "Cidade não cadastrada");
                return false;
            }
        }catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro de SQL"+erro.getMessage());
            return false;
        }
    }
    
    public boolean alterar(Cidade obj){
        String sql = "update cidade set nome = ?, uf=? where codigo = ?";
        try{
            PreparedStatement pst = Conexao.getPreparedStatement(sql);
            pst.setString(1, obj.getNome());
            pst.setString(1, obj.getUf());
            pst.setInt(3, obj.getCodigo());
            if(pst.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Cidade alterada com sucesso");
            return true;
            }else {
                JOptionPane.showMessageDialog(null, "Cidade não alterada");
                return false;
            }
        }catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro de SQL"+erro.getMessage());
            return false;
        }
    }
   
    public boolean remover(Cidade obj){
        String sql = "delete from cidade where codigo = ?";
        try{
            PreparedStatement pst = Conexao.getPreparedStatement(sql);     
            pst.setInt(1, obj.getCodigo());
            if(pst.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Cidade excluida com sucesso");
            return true;
            }else {
                JOptionPane.showMessageDialog(null, "Cidade não excluida");
                return false;
            }
        }catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro de SQL"+erro.getMessage());
            return false;
        }
    }
    
    public Cidade localizar(Integer id){
       String sql = "select * from cidade where codigo=?";
       Cidade objCidade = new Cidade();
       try{
            PreparedStatement pst = Conexao.getPreparedStatement(sql);     
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
        while(rs.next()){
            objCidade.setCodigo(rs.getInt("codigo"));
            objCidade.setNome(rs.getString("nome"));
            objCidade.setUf(rs.getString("uf"));
            return objCidade;
        }
            }
         catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro de SQL"+erro.getMessage());
            }
        
        return null;
    }
}
    
    

