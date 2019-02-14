package fr.asheart.mgvpc;

import java.util.ArrayList;

class Commande {

    private String              idCmd;
    private ArrayList<Produit>  ProduitDeMaCommande = new ArrayList();


    public Commande() {}



    public String getIdCmd() {
        return idCmd;
    }

    public void setIdCmd(String idCmd) {
        this.idCmd = idCmd;
    }

    public ArrayList<Produit> getProduitDeMaCommande() {
        return ProduitDeMaCommande;
    }

    public void setProduitDeMaCommande(ArrayList<Produit> produitDeMaCommande) {
        ProduitDeMaCommande = produitDeMaCommande;
    }



}
