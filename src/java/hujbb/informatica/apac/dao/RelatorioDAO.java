package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.entidades.relarotios.Relatorio;
import hujbb.informatica.apac.util.F;
import hujbb.informatica.apac.util.FabricaDeConexoes;
import hujbb.informatica.apac.util.execao.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioDAO {

    public List<Relatorio> quantitativoCID(String condicao) throws ErroSistema {
        String condicao2 = " WHERE (`formulario`.`status_id_status` <> 1) ";//novo
        if (condicao.isEmpty()) {
            condicao = condicao2;
        } else {
            condicao = condicao2 + " AND (cid.`cid` LIKE '" + condicao + "%' OR cid.`nome` LIKE '" + condicao + "%'  ) ";
        }

        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + " `formulario`.`status_id_status` AS status,\n"
                    + " cid.`cid` AS cid_cid,"
                    + " cid.`nome` AS cid_nome,\n"
                    + " COUNT(`formulario`.`status_id_status`)  AS cont\n"
                    + "FROM\n"
                    + "     `proc_justificativa` proc_justificativa INNER JOIN `formulario` formulario ON proc_justificativa.`id_proc_justificativa` = formulario.`proc_justificativa_id`\n"
                    + "     INNER JOIN `cid` cid ON proc_justificativa.`cid_cid_principal` = cid.`cid` " + condicao + " "
                    + " GROUP BY cid.`cid`, `formulario`.`status_id_status` ORDER BY cid.`cid` \n"
                    + "     ";
            System.out.println(sql);
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Relatorio> rels = new ArrayList<>();
            Map<String, Relatorio> rAux = new HashMap<String, Relatorio>();

            while (rs.next()) {
                Relatorio r;
                String id = rs.getString("cid_cid");
                if (rAux.containsKey(id)) {
                    r = rAux.get(id);
                } else {
                    r = new Relatorio();
                }
                r.setDescricao(rs.getString("cid_cid") + "-" + rs.getString("cid_nome"));

                switch (rs.getInt("status")) {
                     case -9: {//cancelado
                        r.setCancelado(rs.getInt("cont"));
                        break;
                    }
                    case -1: {//nao autorizado
                        r.setNaoAutorizado(rs.getInt("cont"));
                        break;
                    }
                    case 2: {//salso
                        r.setSalvo(rs.getInt("cont"));
                        break;
                    }
                    case 3: {//emitido
                        r.setEmitido(rs.getInt("cont"));
                        break;
                    }
                    case 4: {//enviado dere
                        r.setEnviadoDere(rs.getInt("cont"));
                        break;
                    }
                    case 5: {//autorizado
                        r.setAutorizado(rs.getInt("cont"));
                        break;
                    }

                    default: {
                        break;
                    }
                }

                rAux.put(rs.getString("cid_cid"), r);
            }
            for (String i : rAux.keySet()) {
                rels.add(rAux.get(i));
            }

            return rels;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do usuário", e);
        }
    }

    public List<Relatorio> quantitativoSETOR(String condicao) throws ErroSistema {

        String condicao2 = " WHERE (`formulario`.`status_id_status` <> 1) ";
        if (condicao.isEmpty()) {
            condicao = condicao2;
        } else {
            condicao = condicao2 + " AND (setor.`sigla` LIKE '" + condicao + "%') ";
        }

        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     setor.`id_setor` AS setor_id_setor,\n"
                    + "     \n"
                    + "     setor.`sigla` AS setor_sigla,\n"
                    + "     formulario.`status_id_status` AS formulario_status_id_status,\n"
                    + "     COUNT(`formulario`.`status_id_status`)  AS cont\n"
                    + "FROM\n"
                    + "     `solicitante` solicitante INNER JOIN `formulario` formulario ON solicitante.`id_solicitante` = \n"
                    + "formulario.`solicitante_id_solicitante`\n"
                    + "     INNER JOIN `usuario` usuario ON solicitante.`usuario_id_usuario` = usuario.`id_usuario`\n"
                    + "     INNER JOIN `setor` setor ON usuario.`setor_id_setor` = setor.`id_setor` " + condicao + " GROUP BY   \n"
                    + "setor.`id_setor`,formulario.`status_id_status`";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Relatorio> rels = new ArrayList<>();
            Map<Integer, Relatorio> rAux = new HashMap<Integer, Relatorio>();

            while (rs.next()) {
                Relatorio r;
                int id = rs.getInt("setor_id_setor");
                if (rAux.containsKey(id)) {
                    r = rAux.get(id);
                } else {
                    r = new Relatorio();
                }
                r.setDescricao(rs.getString("setor_sigla"));

                switch (rs.getInt("formulario_status_id_status")) {
                     case -9: {//cancelado
                        r.setCancelado(rs.getInt("cont"));
                        break;
                    }
                    case -1: {//nao autorizado
                        r.setNaoAutorizado(rs.getInt("cont"));
                        break;
                    }
                    case 2: {//salso
                        r.setSalvo(rs.getInt("cont"));
                        break;
                    }
                    case 3: {//emitido
                        r.setEmitido(rs.getInt("cont"));
                        break;
                    }
                    case 4: {//enviado dere
                        r.setEnviadoDere(rs.getInt("cont"));
                        break;
                    }
                    case 5: {//autorizado
                        r.setAutorizado(rs.getInt("cont"));
                        break;
                    }

                    default: {
                        break;
                    }
                }

                rAux.put(rs.getInt("setor_id_setor"), r);
            }
            for (Integer i : rAux.keySet()) {
                rels.add(rAux.get(i));
            }

            return rels;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do usuário", e);
        }
    }

    public List<Relatorio> quantitativoSOLICITANTE(String condicao) throws ErroSistema {

        String condicao2 = " WHERE (`formulario`.`status_id_status` <> 1) ";
        if (condicao.isEmpty()) {
            condicao = condicao2;
        } else {
            condicao = condicao2 + " AND (solicitante.`nome` LIKE '" + condicao + "%') ";
        }
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     formulario.`status_id_status` AS formulario_status_id_status,\n"
                    + "     solicitante.`id_solicitante` AS solicitante_id_solicitante,\n"
                    + "     solicitante.`nome` AS solicitante_nome,\n"
                    + "     solicitante.`cpf` AS solicitante_cpf,\n"
                    + "COUNT(`formulario`.`status_id_status`)  AS cont\n"
                    + "FROM\n"
                    + "     `solicitante` solicitante INNER JOIN `formulario` formulario ON solicitante.`id_solicitante` = formulario.`solicitante_id_solicitante`" + condicao + "  GROUP BY formulario.`status_id_status`, solicitante.`id_solicitante`";

            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Relatorio> rels = new ArrayList<>();
            Map<Integer, Relatorio> rAux = new HashMap<Integer, Relatorio>();

            while (rs.next()) {
                Relatorio r;
                int id = rs.getInt("solicitante_id_solicitante");
                if (rAux.containsKey(id)) {
                    r = rAux.get(id);
                } else {
                    r = new Relatorio();
                }
                r.setDescricao(rs.getString("solicitante_nome"));

                switch (rs.getInt("formulario_status_id_status")) {

                    case -9: {//cancelado
                        r.setCancelado(rs.getInt("cont"));
                        break;
                    }
                    case -1: {//nao autorizado
                        r.setNaoAutorizado(rs.getInt("cont"));
                        break;
                    }
                    case 2: {//salso
                        r.setSalvo(rs.getInt("cont"));
                        break;
                    }
                    case 3: {//emitido
                        r.setEmitido(rs.getInt("cont"));
                        break;
                    }
                    case 4: {//enviado dere
                        r.setEnviadoDere(rs.getInt("cont"));
                        break;
                    }
                    case 5: {//autorizado
                        r.setAutorizado(rs.getInt("cont"));
                        break;
                    }

                    default: {
                        break;
                    }
                }

                rAux.put(rs.getInt("solicitante_id_solicitante"), r);
            }
            for (Integer i : rAux.keySet()) {
                rels.add(rAux.get(i));
            }

            return rels;

        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar dados do usuário", e);
        }
    }

    public List<Relatorio> quantitativopPROCEDIMENTO(String condicao) throws ErroSistema {

        String condicao2 = " WHERE (`formulario`.`status_id_status` <> 1) ";
        if (condicao.isEmpty()) {
            condicao = condicao2;
        } else {
            condicao = condicao2 + " AND (procedimento_sus.`nome` LIKE '" + condicao + "%' OR procedimento_sus.`codigo`  LIKE '" + condicao + "%') ";
        }
        try {
            Connection conexao = FabricaDeConexoes.getConexao();
            String sql = "SELECT\n"
                    + "     formulario.`status_id_status` AS formulario_status_id_status,\n"
                    + "     procedimento_sus.`codigo` AS procedimento_sus_codigo,\n"
                    + "     procedimento_sus.`nome` AS procedimento_sus_nome,\n"
                    + "     formulario.`id_formulario` AS formulario_id_formulario,\n"
                    + "    COUNT(`formulario`.`status_id_status`)  AS cont\n"
                    + "FROM\n"
                    + "     `formulario` formulario INNER JOIN `formulario_has_procedimento_sus` formulario_has_procedimento_sus ON formulario.`id_formulario` = formulario_has_procedimento_sus.`formulario_id_formulario`\n"
                    + "     INNER JOIN `procedimento_sus` procedimento_sus ON formulario_has_procedimento_sus.`procedimento_sus_codigo` = procedimento_sus.`codigo` " + condicao
                    + "  GROUP BY formulario.`status_id_status`,  procedimento_sus.`codigo` ORDER BY procedimento_sus.`nome` ";
            
             // F.setMsgErro(sql);
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Relatorio> rels = new ArrayList<>();
            Map<String, Relatorio> rAux = new HashMap<String, Relatorio>();

            while (rs.next()) {
                Relatorio r;
                String id = rs.getString("procedimento_sus_codigo");
                if (rAux.containsKey(id)) {
                    r = rAux.get(id);
                } else {
                    r = new Relatorio();
                }
                r.setDescricao(rs.getString("procedimento_sus_codigo") + " - " + rs.getString("procedimento_sus_nome"));

                switch (rs.getInt("formulario_status_id_status")) {
                     case -9: {//cancelado
                        r.setCancelado(rs.getInt("cont"));
                        break;
                    }
                    case -1: {//nao autorizado
                        r.setNaoAutorizado(rs.getInt("cont"));
                        break;
                    }
                    case 2: {//salso
                        r.setSalvo(rs.getInt("cont"));
                        break;
                    }
                    case 3: {//emitido
                        r.setEmitido(rs.getInt("cont"));
                        break;
                    }
                    case 4: {//enviado dere
                        r.setEnviadoDere(rs.getInt("cont"));
                        break;
                    }
                    case 5: {//autorizado
                        r.setAutorizado(rs.getInt("cont"));
                        break;
                    }

                    default: {
                        break;
                    }
                }

                rAux.put(rs.getString("procedimento_sus_codigo"), r);
            }
            for (String i : rAux.keySet()) {
                rels.add(rAux.get(i));
            }

            return rels;

        } catch (SQLException e) {
            F.setMsgErro("relatorioDAO:quantitativopPROCEDIMENTO():"+e.toString());
        }
        return null;
    }

}
