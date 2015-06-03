package ipn.cic.layout;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class LayoutComposer extends GenericForwardComposer{
	
	/* ListModel es una lista (List) usada con Listbox.
	 * Add or remove the contents of this model as a List would
	 */
	ListModelList menuModel = new ListModelList();
	Listbox menuListbox;
	NodoMenuListener listener = new NodoMenuListener();
	ItemNodoMenu item = new ItemNodoMenu();
	Div contentDiv;
	
	
	public LayoutComposer(){
		menuModel.add(new NodoMenu("Parametros de visualizacion","/zk/arbol.zul"));
		menuModel.add(new NodoMenu("Conexion a bases de datos","/zk/conexion.zul"));
		menuModel.add(new NodoMenu("CIC-IPN","/zk/cic.zul"));
	}
	
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		menuListbox.setModel(menuModel);
		menuListbox.setItemRenderer(item);
		menuListbox.addEventListener(Events.ON_SELECT,listener);
	}

	class NodoMenu {
		String label;
		String link;
		public NodoMenu(String label,String link){
			this.label = label;
			this.link = link;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
	}
	
	/*
	 * "Sellos de goma" para pintar las celdas de un listbox
	 */
	
	class ItemNodoMenu implements ListitemRenderer{

		public void render(Listitem item, Object data) throws Exception {
			NodoMenu node = (NodoMenu)data;
			item.setImage("icon-24x24.png");
			item.setLabel(node.getLabel());
			item.setValue(node);
		}
	}
	
	class NodoMenuListener implements EventListener{
		public void onEvent(Event event) throws Exception {
			Listitem item = menuListbox.getSelectedItem();
			contentDiv.getChildren().clear();
			if(item!=null){
				NodoMenu node = (NodoMenu)item.getValue();
				Executions.createComponents(node.getLink(),contentDiv,null);
			}
		}		
	}
	
}
