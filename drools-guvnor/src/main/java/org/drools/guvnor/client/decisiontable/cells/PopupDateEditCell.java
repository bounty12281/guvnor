package org.drools.guvnor.client.decisiontable.cells;

import java.util.Date;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * A Popup Date Editor.
 * 
 * @author manstis
 * 
 */
public class PopupDateEditCell extends AbstractPopupEditCell<Date, Date> {

	private final DatePicker datePicker;
	private final DateTimeFormat format;

	public PopupDateEditCell(DateTimeFormat format) {
		super();
		if (format == null) {
			throw new IllegalArgumentException("format == null");
		}

		this.format = format;
		this.datePicker = new DatePicker();

		// Hide the panel and call valueUpdater.update when a date is selected
		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				// Remember the values before hiding the popup.
				Element cellParent = lastParent;
				Date oldValue = lastValue;
				Object key = lastKey;
				panel.hide();

				// Update the cell and value updater.
				Date date = event.getValue();
				setViewData(key, date);
				setValue(cellParent, oldValue, key);
				if (valueUpdater != null) {
					valueUpdater.update(date);
				}
			}
		});

		vPanel.add(datePicker);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.cell.client.AbstractCell#render(java.lang.Object,
	 * java.lang.Object, com.google.gwt.safehtml.shared.SafeHtmlBuilder)
	 */
	@Override
	public void render(Date value, Object key, SafeHtmlBuilder sb) {
		// Get the view data.
		Date viewData = getViewData(key);
		if (viewData != null && viewData.equals(value)) {
			clearViewData(key);
			viewData = null;
		}

		String s = null;
		if (viewData != null) {
			s = format.format(viewData);
		} else if (value != null) {
			s = format.format(value);
		}
		if (s != null) {
			sb.append(renderer.render(s));
		}
	}

	// Commit the change
	private void commit(Date date) {
		// Hide pop-up
		Element cellParent = lastParent;
		Date oldValue = lastValue;
		Object key = lastKey;
		panel.hide();

		// Update values
		setViewData(key, date);
		setValue(cellParent, oldValue, key);
		if (valueUpdater != null) {
			valueUpdater.update(date);
		}
	}

	// Commit the change
	@Override
	protected void commit() {
		Date date = datePicker.getValue();
		commit(date);
	}

	// Start editing the cell
	@Override
	@SuppressWarnings("deprecation")
	protected void startEditing(final Element parent, Date value, Object key) {

		Date viewData = getViewData(key);
		Date date = (viewData == null) ? value : viewData;

		// Default date
		if (date == null) {
			Date d = new Date();
			int year = d.getYear();
			int month = d.getMonth();
			int dom = d.getDate();
			date = new Date(year, month, dom);
		}
		datePicker.setCurrentMonth(date);
		datePicker.setValue(date);

		panel.setPopupPositionAndShow(new PositionCallback() {
			public void setPosition(int offsetWidth, int offsetHeight) {
				panel.setPopupPosition(parent.getAbsoluteLeft() + offsetX,
						parent.getAbsoluteTop() + offsetY);
			}
		});

	}

}