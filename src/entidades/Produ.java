package entidades;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import tela.App;
import conexao.Conexao;

public class Produ {
	Connection pg = Conexao.getPostgresConnection();
	Connection vmd = Conexao.getSqlConnection();
	
	App a = new App();
	
	public void importa(JProgressBar progressBar2, JLabel lblNewLabel_5) throws Exception {
		String pgPRODU= "select cod_reduzido, nom_produto, dat_cadastro, cod_laborat, cod_barra, vlr_venda, vlr_custo, flg_ativo, flg_descvenda from cadprodu";
		String vPRODU = "Insert Into Produ (Cod_Produt, Des_Produt, Des_Resumi, Des_Comple, Dat_Implan, Cod_Fabric, Cod_Ean, Cod_Classi, Cod_Seccao, Cod_GrpPrc) Values (?,?,?,?,?,?,?,?,?,?)";
		
		try (PreparedStatement pVmd = vmd.prepareStatement(vPRODU);
			 PreparedStatement pPg = pg.prepareStatement(pgPRODU)) {
			
			ResultSet rs = pPg.executeQuery();
			
			// contar a qtde de registros
			int registros = a.contaRegistros("cadprodu");
			int total = registros;
			progressBar2.setMaximum(registros);
			registros = 0;
			
			while (rs.next()) {
				
				// grava no varejo
				int codigo = rs.getInt("cod_reduzido");
				pVmd.setInt(1, codigo);
				
				String nome = rs.getString("nom_produto");
				if(nome != null) {
					pVmd.setString(2, nome.length() > 40 ? nome.substring(0, 40) : nome);
					pVmd.setString(3, nome.length() > 24 ? nome.substring(0, 24) : nome);
					pVmd.setString(4, nome.length() > 50 ? nome.substring(0, 50) : nome);
				}else{
					pVmd.setString(2, nome);
					pVmd.setString(3, nome);
					pVmd.setString(4, nome);
				}
				
				Date data_cadastro = rs.getDate("dat_cadastro");
				pVmd.setDate(5, data_cadastro);
				
			
				String cod_fabricante = rs.getString("cod_laborat");
				if (cod_fabricante != null) {
					pVmd.setString(6, cod_fabricante);
				}else {
					pVmd.setString(6, "9999");
				}
				
				String ean = rs.getString("cod_barra");
				pVmd.setString(7, ean);
				
				pVmd.setString(8, "0199");
				pVmd.setString(9, "10");
				pVmd.setString(10, "A");
				
				pVmd.executeUpdate();
				
				registros++;
				lblNewLabel_5.setText(registros+"/"+total);
				progressBar2.setValue(registros);
			}
			
			System.out.println("Funcionou Produ");
			pVmd.close();
			pPg.close();
			
			progressBar2.setValue(0);

		}
	}
	
}
