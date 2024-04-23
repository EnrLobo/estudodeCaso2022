/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuário
 */
public class DAOFuncionario {
    
    DAOCidade daoCidade = new DAOCidade();
    
        public List<Funcionario> getLista(){     
        String sql = "select * from Funcionario";
        List<Funcionario> listaFuncionario = new ArrayList<>();
        try{
            PreparedStatement pst = Conexao.getPreparedStatement(sql);
            ResultSet rs = pst.executeQuery();
        while(rs.next()){
            Funcionario objFuncionario = new Funcionario();
            objFuncionario.setCodigo(rs.getInt("codigo"));
            objFuncionario.setNome(rs.getString("nome"));
            objFuncionario.setSalario(rs.getDouble("salario"));
            java.sql.Date dt = rs.getDate("nascimento");
            Calendar c= Calendar.getInstance();
            c.setTime(dt);
            objFuncionario.setNascimento(c);
            objFuncionario.setCidade(daoCidade.localizar(rs.getInt("cidade")));
            
            listaFuncionario.add(objFuncionario);
        }
        }catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro de SQL"+erro.getMessage());
        }            
        return listaFuncionario;
        
    }
    
    public boolean salvar (Funcionario obj){
        if(obj.getCodigo()==null){
            return incluir(obj);            
        }else{
            return alterar(obj);
            
        }               

    }
    
    
    public boolean incluir(Funcionario obj){
        String sql = "insert into Funcionario (nome, salario, nascimento, cidade) values(?,?,?,?)";
        try{
            PreparedStatement pst = Conexao.getPreparedStatement(sql);
            pst.setString(1, obj.getNome());
            pst.setDouble(2, obj.getSalario());
            pst.setDate(3, new java.sql.Date(obj.getNascimento().getTimeInMillis()));
            pst.setInt(4, obj.getCidade().getCodigo());
            
            
            if(pst.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Funcionario cadastrado com sucesso");
            return true;
            }else {
                JOptionPane.showMessageDialog(null, "Funcionario não cadastrado");
                return false;
            }
        }catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro de SQL"+erro.getMessage());
            return false;
        }
    }
    
    public boolean alterar(Funcionario obj){
        String sql = "update Funcionario set nome = ?, salario=?, nascimento=?, cidade=? where codigo = ?";
        try{
            PreparedStatement pst = Conexao.getPreparedStatement(sql);
            pst.setString(1, obj.getNome());
            pst.setDouble(2, obj.getSalario());
            pst.setDate(3, new java.sql.Date(obj.getNascimento().getTimeInMillis()));
            pst.setInt(4, obj.getCidade().getCodigo());
            pst.setInt(5, obj.getCodigo());
            
            if(pst.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Funcionario alterado com sucesso");
            return true;
            }else {
                JOptionPane.showMessageDialog(null, "Funcionario não alterado");
                return false;
            }
        }catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro de SQL"+erro.getMessage());
            return false;
        }
    }
   
    public boolean remover(Funcionario obj){
        String sql = "delete from Funcionario where codigo = ?";
        try{
            PreparedStatement pst = Conexao.getPreparedStatement(sql);     
            pst.setInt(1, obj.getCodigo());
            if(pst.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Funcionario excluido com sucesso");
            return true;
            }else {
                JOptionPane.showMessageDialog(null, "Funcionario não excluido");
                return false;
            }
        }catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro de SQL"+erro.getMessage());
            return false;
        }
    }
    
}

    

