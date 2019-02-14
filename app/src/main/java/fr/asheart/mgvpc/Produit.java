package fr.asheart.mgvpc;

class Produit {
    private String idProd;
    private String emplacement;
    private String lib;
    private String qte;

    public Produit(String idProd, String emplacement, String lib, String qte) {
        this.idProd = idProd;
        this.emplacement = emplacement;
        this.lib = lib;
        this.qte = qte;
    }

    public String getIdProd()       { return idProd;        }
    public String getEmplacement()  { return emplacement;   }
    public String getLib()          { return lib;           }
    public String getQte()          { return qte;           }

    public void setIdProd       (String idProd)         { this.idProd = idProd;             }
    public void setEmplacement  (String emplacement)    { this.emplacement = emplacement;   }
    public void setLib          (String lib)            { this.lib = lib;                   }
    public void setQte          (String qte)            { this.qte = qte;                   }
}
