package com.app.gst;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoiceline;
import com.axelor.gst.db.Party;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InvoicelineData {

  public void setInvoiceData(ActionRequest request, ActionResponse response) {
    Invoice invoice = request.getContext().asType(Invoice.class);
    double netamount = 0, netigst = 0, netcsgst = 0, netsgst = 0, grossamount = 0;
    ArrayList<Invoiceline> il = (ArrayList<Invoiceline>) invoice.getInvoiceitemList();

    for (Invoiceline invoiceline : il) {
      netamount = netamount + invoiceline.getNetAmount().doubleValue();
      netigst = netigst + invoiceline.getIgst().doubleValue();
      netcsgst = netcsgst + invoiceline.getCgst().doubleValue();
      netsgst = netsgst + invoiceline.getSgst().doubleValue();
      grossamount = grossamount + netamount + netcsgst + netigst + netsgst;
    }

    invoice.setNetAmount(new BigDecimal(netamount));
    invoice.setNetIgst(new BigDecimal(netigst));
    invoice.setNetCsgst(new BigDecimal(netcsgst));
    invoice.setNetSgst(new BigDecimal(netsgst));
    invoice.setGrossAmount(new BigDecimal(grossamount));

    response.setValues(invoice);
  }

  public void setInvoiceLineData(ActionRequest request, ActionResponse response) {

    Invoiceline invoiceline = request.getContext().asType(Invoiceline.class);
    Invoice invoice = request.getContext().getParent().asType(Invoice.class);
    BigDecimal n;
    Address companyAddress = invoice.getCompany().getAddress();
    Address invoiceAddress = invoice.getInvoiceAddress();

    BigDecimal netamount, netigst, netcsgst, netsgst, grossamount = new BigDecimal(0);
    BigDecimal b = new BigDecimal(invoiceline.getQty());
    netamount = invoiceline.getPrice().multiply(b);

    System.out.println(netamount.toString());
    if (companyAddress.getState().equals(invoiceAddress.getState())) {
      netigst = new BigDecimal(0);
      netcsgst =
          netamount
              .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
              .divide(new BigDecimal(2));
      netsgst =
          netamount
              .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
              .divide(new BigDecimal(2));
    } else {
      netcsgst = new BigDecimal(0);
      netsgst = new BigDecimal(0);
      netigst =
          netamount
              .multiply(invoiceline.getGstrate().divide(new BigDecimal(100)))
              .divide(new BigDecimal(2));
    }

    grossamount = grossamount.add(netamount).add(netcsgst).add(netigst).add(netsgst);
    System.err.println(grossamount);
    invoiceline.setNetAmount(netamount);
    invoiceline.setIgst(netigst);
    invoiceline.setCgst(netcsgst);
    invoiceline.setSgst(netsgst);
    invoiceline.setGrossAmount(grossamount);

    response.setValues(invoiceline);
  }

  public void setinvoiceDefaultfield(ActionRequest request, ActionResponse respons) {

    Invoice invoice = request.getContext().asType(Invoice.class);
    System.err.println("hello call");
    try {
      Party party = invoice.getParty();
      if (party != null) {
        List<Contact> contactList = party.getContactList();
        if (contactList != null && !contactList.isEmpty()) {
          for (Contact contact : contactList) {
            if (contact.getType().equals("Primary")) {
              invoice.setPartyContact(contact);
              break;
            }
          }

          List<Address> addressList = party.getAddressList();
          for (Address address : addressList) {
            if (address.getType().equals("default") || address.getType().equals("invoice")) {
              invoice.setInvoiceAddress(address);
              break;
            }
          }
          if (!invoice.getIsUseInVoiceAddressAsShipping()) {
            for (Address address : addressList) {
              if (address.getType().equals("default") || address.getType().equals("shipping")) {
                invoice.setShippingAddress(address);
                break;
              }
            }
          } else {
            for (Address address : addressList) {
              if (address.getType().equals("default") || address.getType().equals("invoice")) {
                invoice.setShippingAddress(address);
                break;
              }
            }
          }
        }
      }

    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.fillInStackTrace());
    }
    respons.setValues(invoice);
  }
}
