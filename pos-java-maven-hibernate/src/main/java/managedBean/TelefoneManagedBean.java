package managedBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DaoTelefones;
import dao.DaoUsuario;
import model.TelefoneUser;
import model.UsuarioPessoa;

@ManagedBean(name = "telefoneManagedBean")
@ViewScoped
public class TelefoneManagedBean {

	private UsuarioPessoa user = new UsuarioPessoa();
	private DaoUsuario<UsuarioPessoa> daoUsuario = new DaoUsuario<UsuarioPessoa>();
	private DaoTelefones<TelefoneUser> daoTelefones = new DaoTelefones<TelefoneUser>();
	private TelefoneUser telefone = new TelefoneUser();
	
	@PostConstruct
	public void init() {
		
		String coduser = FacesContext.getCurrentInstance()
						 .getExternalContext()
						 .getRequestParameterMap()
						 .get("codigouser");
		user = daoUsuario.pesquisar(Long.parseLong(coduser), UsuarioPessoa.class);
	}
	
	public String salvar() {
		telefone.setUsuarioPessoa(user);
		
		daoTelefones.salvar(telefone);
		
		telefone = new TelefoneUser();
		
		user = daoUsuario.pesquisar(user.getId(), UsuarioPessoa.class);
		
		FacesContext.getCurrentInstance()
		.addMessage(null, new FacesMessage
		(FacesMessage.SEVERITY_INFO, "Informação: ", "Salvo com sucesso!"));
		
		return "";
	}
	
	public String removeTelefone() throws Exception {
		
		daoTelefones.deletarPorId(telefone);
		user = daoUsuario.pesquisar(user.getId(), UsuarioPessoa.class);
		
		telefone = new TelefoneUser();
		
		FacesContext.getCurrentInstance()
		.addMessage(null, new FacesMessage
		(FacesMessage.SEVERITY_INFO, "Informação: ", "Removido com sucesso!"));
		
		return "";
	}
	
	public TelefoneUser getTelefone() {
		return telefone;
	}
	public void setTelefone(TelefoneUser telefoneUser) {
		this.telefone = telefoneUser;
	}
	public DaoTelefones<TelefoneUser> getDaoTelefones() {
		return daoTelefones;
	}
	public void setDaoTelefones(DaoTelefones<TelefoneUser> daoTelefones) {
		this.daoTelefones = daoTelefones;
	}
	public UsuarioPessoa getUser() {
		return user;
	}
	public void setUser(UsuarioPessoa user) {
		this.user = user;
	}
	
}
