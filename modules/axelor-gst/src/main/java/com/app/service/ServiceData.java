package com.app.service;

import java.util.ArrayList;

import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoiceline;

public interface ServiceData {
	public Invoice setInvoiceDataService(Invoice invoice,ArrayList<Invoiceline> il);
	public Invoiceline setInvoicelineData(Invoice invoice,Invoiceline invoiceline);
	public Invoice setPartyData(Invoice invoice);
	public Invoiceline setPartyCompanyChangeData(Invoice invoice, Invoiceline invoiceline);
}
