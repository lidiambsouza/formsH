
package hujbb.informatica.apac.dao;

import hujbb.informatica.apac.util.execao.ErroSistema;
import java.util.List;


public interface CrudDAO<E>  {//E representa a entidade
    public E salvar(E entidade) throws ErroSistema;
    
    public E atualizar(E entidade) throws ErroSistema;
    
    public E deletar(E entidade) throws ErroSistema;
    public List<E> buscar(String condicao)throws ErroSistema;
    public E buscaId(String id) throws ErroSistema;
    
    
}
