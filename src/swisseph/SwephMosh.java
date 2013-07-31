/*
   This is a port of the Swiss Ephemeris Free Edition, Version 1.76.00
   of Astrodienst AG, Switzerland from the original C Code to Java. For
   copyright see the original copyright notices below and additional
   copyright notes in the file named LICENSE, or - if this file is not
   available - the copyright notes at http://www.astro.ch/swisseph/ and
   following. 

   For any questions or comments regarding this port to Java, you should
   ONLY contact me and not Astrodienst, as the Astrodienst AG is not involved
   in this port in any way.

   Thomas Mack, mack@ifis.cs.tu-bs.de, 23rd of April 2001

*/
/* Copyright (C) 1997 - 2008 Astrodienst AG, Switzerland.  All rights reserved.

  License conditions
  ------------------

  This file is part of Swiss Ephemeris.

  Swiss Ephemeris is distributed with NO WARRANTY OF ANY KIND.  No author
  or distributor accepts any responsibility for the consequences of using it,
  or for whether it serves any particular purpose or works at all, unless he
  or she says so in writing.

  Swiss Ephemeris is made available by its authors under a dual licensing
  system. The software developer, who uses any part of Swiss Ephemeris
  in his or her software, must choose between one of the two license models,
  which are
  a) GNU public license version 2 or later
  b) Swiss Ephemeris Professional License

  The choice must be made before the software developer distributes software
  containing parts of Swiss Ephemeris to others, and before any public
  service using the developed software is activated.

  If the developer choses the GNU GPL software license, he or she must fulfill
  the conditions of that license, which includes the obligation to place his
  or her whole software project under the GNU GPL or a compatible license.
  See http://www.gnu.org/licenses/old-licenses/gpl-2.0.html

  If the developer choses the Swiss Ephemeris Professional license,
  he must follow the instructions as found in http://www.astro.com/swisseph/
  and purchase the Swiss Ephemeris Professional Edition from Astrodienst
  and sign the corresponding license contract.

  The License grants you the right to use, copy, modify and redistribute
  Swiss Ephemeris, but only under certain conditions described in the License.
  Among other things, the License requires that the copyright notices and
  this notice be preserved on all copies.

  Authors of the Swiss Ephemeris: Dieter Koch and Alois Treindl

  The authors of Swiss Ephemeris have no control or influence over any of
  the derived works, i.e. over software or services created by other
  programmers which use Swiss Ephemeris functions.

  The names of the authors or of the copyright holder (Astrodienst) must not
  be used for promoting any software, product or service which uses or contains
  the Swiss Ephemeris. This copyright notice is the ONLY place where the
  names of the authors can legally appear, except in cases where they have
  given special permission in writing.

  The trademarks 'Swiss Ephemeris' and 'Swiss Ephemeris inside' may be used
  for promoting such software, products or services.
*/
package swisseph;

class SwephMosh
		implements java.io.Serializable
		{

  SwissLib sl=null;
  SwissEph sw=null;
  SwissData swed=null;
  Swemmoon sm=null;
  SweDate sd=null;


  private static final double TIMESCALE=3652500.0;
  private static final int FICT_GEO=1;
  private static final int pnoint2msh[] = {2, 2, 0, 1, 3, 4, 5, 6, 7, 8};

  /* From Simon et al (1994)  */
  private static final double freqs[] = {
  /* Arc sec per 10000 Julian years.  */
    53810162868.8982,
    21066413643.3548,
    12959774228.3429,
    6890507749.3988,
    1092566037.7991,
    439960985.5372,
    154248119.3933,
    78655032.0744,
    52272245.1795
  };

  private static final double phases[] = {
  /* Arc sec.  */
    252.25090552 * 3600.,
    181.97980085 * 3600.,
    100.46645683 * 3600.,
    355.43299958 * 3600.,
    34.35151874 * 3600.,
    50.07744430 * 3600.,
    314.05500511 * 3600.,
    304.34866548 * 3600.,
    860492.1546,
  };

  double ss[][]=new double[9][24];
  double cc[][]=new double[9][24];



  SwephMosh(SwissLib sl, SwissEph sw, SwissData swed) {
    this.sl    = sl;
    this.sw    = sw;
    this.swed  = swed;
    this.sm    = new Swemmoon();
    if (this.sl   ==null) { this.sl   =new SwissLib(); }
    if (this.sw   ==null) { this.sw   =new SwissEph(); }
    if (this.swed ==null) { this.swed =new SwissData(); }
  }



  /* orbital elements of planets that are computed from osculating elements
   *   epoch
   *   equinox
   *   mean anomaly,
   *   semi axis,
   *   eccentricity,
   *   argument of perihelion,
   *   ascending node
   *   inclination
   */
                                  /* use James Neely's revised elements
                                   *      of Uranian planets*/
  static final String plan_fict_nam[] =
    {"Cupido", "Hades", "Zeus", "Kronos",
     "Apollon", "Admetos", "Vulkanus", "Poseidon",
     "Isis-Transpluto", "Nibiru", "Harrington",
     "Leverrier", "Adams",
     "Lowell", "Pickering",};

  String swi_get_fict_name(int ipl, String snam) {
    if (snam==null) { snam=""; }
    StringBuffer sbnam=new StringBuffer(snam);
    if (read_elements_file(ipl, 0, null, null,
         null, null, null, null, null, null,
         sbnam, null, null) == SweConst.ERR) {
      return "name not found";
    }
    return sbnam.toString();
  }

  private static final double plan_oscu_elem[][]=new double[][] {
    {SwephData.J1900, SwephData.J1900, 163.7409, 40.99837, 0.00460, 171.4333, 129.8325, 1.0833},/* Cupido Neely */
    {SwephData.J1900, SwephData.J1900,  27.6496, 50.66744, 0.00245, 148.1796, 161.3339, 1.0500},/* Hades Neely */
    {SwephData.J1900, SwephData.J1900, 165.1232, 59.21436, 0.00120, 299.0440,   0.0000, 0.0000},/* Zeus Neely */
    {SwephData.J1900, SwephData.J1900, 169.0193, 64.81960, 0.00305, 208.8801,   0.0000, 0.0000},/* Kronos Neely */
    {SwephData.J1900, SwephData.J1900, 138.0533, 70.29949, 0.00000,   0.0000,   0.0000, 0.0000},/* Apollon Neely */
    {SwephData.J1900, SwephData.J1900, 351.3350, 73.62765, 0.00000,   0.0000,   0.0000, 0.0000},/* Admetos Neely */
    {SwephData.J1900, SwephData.J1900,  55.8983, 77.25568, 0.00000,   0.0000,   0.0000, 0.0000},/* Vulcanus Neely */
    {SwephData.J1900, SwephData.J1900, 165.5163, 83.66907, 0.00000,   0.0000,   0.0000, 0.0000},/* Poseidon Neely */
    /* Isis-Transpluto; elements from "Die Sterne" 3/1952, p. 70ff.
     * Strubell does not give an equinox. 1945 is taken to best reproduce
     * ASTRON ephemeris. (This is a strange choice, though.)
     * The epoch is 1772.76. The year is understood to have 366 days.
     * The fraction is counted from 1 Jan. 1772 */
    {2368547.66, 2431456.5, 0.0, 77.775, 0.3, 0.7, 0, 0},
    /* Nibiru, elements from Christian Woeltge, Hannover */
    {1856113.380954, 1856113.380954, 0.0, 234.8921, 0.981092, 103.966, -44.567, 158.708},
    /* Harrington, elements from Astronomical Journal 96(4), Oct. 1988 */
    {2374696.5, SwephData.J2000, 0.0, 101.2, 0.411, 208.5, 275.4, 32.4},
    /* Leverrier's Neptune,
          according to W.G. Hoyt, "Planets X and Pluto", Tucson 1980, p. 63 */
    {2395662.5, 2395662.5, 34.05, 36.15, 0.10761, 284.75, 0, 0},
    /* Adam's Neptune */
    {2395662.5, 2395662.5, 24.28, 37.25, 0.12062, 299.11, 0, 0},
    /* Lowell's Pluto */
    {2425977.5, 2425977.5, 281, 43.0, 0.202, 204.9, 0, 0},
    /* Pickering's Pluto */
    {2425977.5, 2425977.5, 48.95, 55.1, 0.31, 280.1, 100, 15}, /**/
  };

  /* computes a planet from osculating elements *
   * tjd          julian day
   * ipl          body number
   * ipli         body number in planetary data structure
   * iflag        flags
   */
  int swi_osc_el_plan(double tjd, double xp[], int ipl, int ipli,
                      double[] xearth, double[] xsun, StringBuffer serr) {
    double pqr[]=new double[9], x[]=new double[6];
    double eps, K, fac, rho, cose, sine;
    double alpha, beta, zeta, sigma, M2, Msgn, M_180_or_0;
    DblObj tjd0=new DblObj();
    DblObj tequ=new DblObj();
    DblObj mano=new DblObj();
    DblObj sema=new DblObj();
    DblObj ecce=new DblObj();
    DblObj parg=new DblObj();
    DblObj node=new DblObj();
    DblObj incl=new DblObj();
    double dmot;
    double cosnode, sinnode, cosincl, sinincl, cosparg, sinparg;
    double M, E;
    PlanData pedp = swed.pldat[SwephData.SEI_EARTH];
    PlanData pdp = swed.pldat[ipli];
    IntObj fict_ifl = new IntObj(); fict_ifl.val = 0;
    int i;
    /* orbital elements, either from file or, if file not found,
     * from above built-in set
     */
    if (read_elements_file(ipl, tjd, tjd0, tequ,
         mano, sema, ecce, parg, node, incl,
         null, fict_ifl, serr) == SweConst.ERR) {
      return SweConst.ERR;
    }
    dmot = 0.9856076686 * SwissData.DEGTORAD / sema.val / SMath.sqrt(sema.val);
                                                            /* daily motion */
    if ((fict_ifl.val & FICT_GEO) != 0) {
      dmot /= SMath.sqrt(SwephData.SUN_EARTH_MRAT);
    }
    cosnode = SMath.cos(node.val);
    sinnode = SMath.sin(node.val);
    cosincl = SMath.cos(incl.val);
    sinincl = SMath.sin(incl.val);
    cosparg = SMath.cos(parg.val);
    sinparg = SMath.sin(parg.val);
    /* Gaussian vector */
    pqr[0] = cosparg * cosnode - sinparg * cosincl * sinnode;
    pqr[1] = -sinparg * cosnode - cosparg * cosincl * sinnode;
    pqr[2] = sinincl * sinnode;
    pqr[3] = cosparg * sinnode + sinparg * cosincl * cosnode;
    pqr[4] = -sinparg * sinnode + cosparg * cosincl * cosnode;
    pqr[5] = -sinincl * cosnode;
    pqr[6] = sinparg * sinincl;
    pqr[7] = cosparg * sinincl;
    pqr[8] = cosincl;
    /* Kepler problem */
    E = M = sl.swi_mod2PI(mano.val + (tjd - tjd0.val) * dmot); /* mean anomaly of date*/
    /* better E for very high eccentricity and small M */
    if (ecce.val > 0.975) {
      M2 = M * SwissData.RADTODEG;
      if (M2 > 150 && M2 < 210) {
        M2 -= 180;
        M_180_or_0 = 180;
      } else
        M_180_or_0 = 0;
      if (M2 > 330) {
        M2 -= 360;
      }
      if (M2 < 0) {
        M2 = -M2;
        Msgn = -1;
      } else {
        Msgn = 1;
      }
      if (M2 < 30) {
        M2 *= SwissData.DEGTORAD;
        alpha = (1 - ecce.val) / (4 * ecce.val + 0.5);
        beta = M2 / (8 * ecce.val + 1);
        zeta = SMath.pow(beta + SMath.sqrt(beta * beta + alpha * alpha), 1/3);
        sigma = zeta - alpha / 2;
        sigma = sigma - 0.078 * sigma * sigma * sigma * sigma * sigma / (1 + ecce.val)
  ;
        E = Msgn * (M2 + ecce.val * (3 * sigma - 4 * sigma * sigma * sigma))
                          + M_180_or_0;
      }
    }
    E = sl.swi_kepler(E, M, ecce.val);
    /* position and speed, referred to orbital plane */
    if ((fict_ifl.val & FICT_GEO) != 0) {
      K = SwephData.KGAUSS_GEO / SMath.sqrt(sema.val); 
    } else {
      K = SwephData.KGAUSS / SMath.sqrt(sema.val);
    }
    cose = SMath.cos(E);
    sine = SMath.sin(E);
    fac = SMath.sqrt((1 - ecce.val) * (1 + ecce.val));
    rho = 1 - ecce.val * cose;
    x[0] = sema.val * (cose - ecce.val);
    x[1] = sema.val * fac * sine;
    x[3] = -K * sine / rho;
    x[4] = K * fac * cose / rho;
    /* transformation to ecliptic */
    xp[0] = pqr[0] * x[0] + pqr[1] * x[1];
    xp[1] = pqr[3] * x[0] + pqr[4] * x[1];
    xp[2] = pqr[6] * x[0] + pqr[7] * x[1];
    xp[3] = pqr[0] * x[3] + pqr[1] * x[4];
    xp[4] = pqr[3] * x[3] + pqr[4] * x[4];
    xp[5] = pqr[6] * x[3] + pqr[7] * x[4];
    /* transformation to equator */
    eps = sl.swi_epsiln(tequ.val);
    sl.swi_coortrf(xp, xp, -eps);
    sl.swi_coortrf(xp, 3, xp, 3, -eps);
    /* precess to J2000 */
    if (tequ.val != SwephData.J2000) {
      sl.swi_precess(xp, tequ.val, SwephData.J_TO_J2000);
      sl.swi_precess(xp, 3, tequ.val, SwephData.J_TO_J2000);
    }
    /* to solar system barycentre */
    if ((fict_ifl.val & FICT_GEO) != 0) {
      for (i = 0; i <= 5; i++) {
        xp[i] += xearth[i];
      }
    } else {
      for (i = 0; i <= 5; i++) {    
        xp[i] += xsun[i];
      }
    }
    if (pdp.x == xp) {
      pdp.teval = tjd;   /* for precession! */
      pdp.iephe = pedp.iephe;
    }
    return SweConst.OK;
  }

  /* note: input parameter tjd is required for T terms in elements */
  private int read_elements_file(int ipl, double tjd,
                                 DblObj tjd0, DblObj tequ,
                                 DblObj mano, DblObj sema, DblObj ecce,
                                 DblObj parg, DblObj node, DblObj incl,
                                 StringBuffer pname, IntObj fict_ifl,
                                 StringBuffer serr) {
    int i, iline, iplan, retc, ncpos;
    FilePtr fp = null;
    String s, sp;
    int spIdx=0;
    String cpos[]=new String[20], serri="";
    boolean elem_found = false;
    double tt = 0;
    /* -1, because file information is not saved, file is always closed */
    try {
      fp = sw.swi_fopen(-1, SweConst.SE_FICTFILE, swed.ephepath, serr);
    } catch (SwissephException se) {
      /* file does not exist, use built-in bodies */
      if (ipl >= SweConst.SE_NFICT_ELEM) {
        if (serr != null) {
          serr.append("error no elements for fictitious body no ").append(ipl);
        }
        return SweConst.ERR;
      }
      if (tjd0 != null) {
        tjd0.val = plan_oscu_elem[ipl][0];                   /* epoch */
      }
      if (tequ != null) {
        tequ.val = plan_oscu_elem[ipl][1];                   /* equinox */
      }
      if (mano != null) {
        mano.val = plan_oscu_elem[ipl][2] * SwissData.DEGTORAD; /* mean anomaly */
      }
      if (sema != null) {
        sema.val = plan_oscu_elem[ipl][3];                   /* semi-axis */
      }
      if (ecce != null) {
        ecce.val = plan_oscu_elem[ipl][4];                   /* eccentricity */
      }
      if (parg != null) {
        parg.val = plan_oscu_elem[ipl][5] * SwissData.DEGTORAD; /* arg. of peri. */
      }
      if (node != null) {
        node.val = plan_oscu_elem[ipl][6] * SwissData.DEGTORAD;  /* asc. node */
      }
      if (incl != null) {
        incl.val = plan_oscu_elem[ipl][7] * SwissData.DEGTORAD; /* inclination*/
      }
      if (pname != null) {
        pname.setLength(0);
        pname.append(plan_fict_nam[ipl]);
      }
      return SweConst.OK;
    }
    /*
     * find elements in file
     */
    iline = 0;
    iplan = -1;
    try {
//    while (fgets(s, AS_MAXCH, fp) != null)
      while ((s=fp.readLine()) != null) {
        s=s.trim();
//        iline++;
//        spIdx = 0;
//        while(s.charAt(spIdx) == ' ' || s.charAt(spIdx) == '\t')
//          spIdx++;
//        s=s.substring(spIdx);
        sp = s;
        spIdx=0;
        char ch=s.charAt(spIdx);
        if (ch == '#' || ch=='\r' || ch=='\n' || ch=='\0') {
          continue;
        }
//    if ((sp = strchr(s, '#')) != NULL)
//      *sp = '\0';
        sp = null;
        if ((spIdx = s.indexOf('#')) >= 0) {
          s = s.substring(0,s.indexOf('#'));
          sp = "";
        }
        ncpos = sl.swi_cutstr(s, ",", cpos, 20);
        serri="error in file "+SweConst.SE_FICTFILE+", line "+
              iline+":";
        if (ncpos < 9) {
          if (serr != null) {
            serr.setLength(0);
            serr.append(serri).append(" nine elements required");
          }
          return SweConst.ERR;
        }
        iplan++;
        if (iplan != ipl) {
          continue;
        }
        elem_found = true;
        /* epoch of elements */
        if (tjd0 != null) {
          sp = cpos[0];
//          for (i = 0; i < 5; i++)
//       sp[i] = tolower(sp[i]);
          sp=sp.length()<=5?sp.toLowerCase():
                               sp.substring(0,5).toLowerCase()+sp.substring(5);
          if (sp.startsWith("j2000")) {
            tjd0.val = SwephData.J2000;
          } else if (sp.startsWith("b1950")) {
            tjd0.val = SwephData.B1950;
          } else if (sp.startsWith("j1900")) {
            tjd0.val = SwephData.J1900;
          } else if (sp.charAt(0) == 'j' || sp.charAt(0) == 'b') {
            if (serr != null) {
              serr.setLength(0);
              serr.append(serri).append(" invalid epoch");
            }
//          goto return_err;
            fp.close(); return SweConst.ERR;
          } else
            tjd0.val = SwissLib.atof(sp);
          tt = tjd - tjd0.val;
        }
        /* equinox */
        if (tequ != null) {
          sp = cpos[1];
          spIdx=0;
          while(sp.charAt(spIdx) == ' ' || sp.charAt(spIdx) == '\t')
            spIdx++;
//          for (i = 0; i < 5; i++)
//       sp[i] = tolower(sp[i]);
          sp=sp.substring(spIdx);
          sp=sp.length()<5?sp.toLowerCase():
                               sp.substring(0,5).toLowerCase()+sp.substring(5);
          if (sp.startsWith("j2000")) {
            tequ.val = SwephData.J2000;
          } else if (sp.startsWith("b1950")) {
            tequ.val = SwephData.B1950;
          } else if (sp.startsWith("j1900")) {
            tequ.val = SwephData.J1900;
          } else if (sp.startsWith("jdate")) {
            tequ.val = tjd;
          } else if (sp.charAt(0) == 'j' || sp.charAt(0) == 'b') {
            if (serr != null) {
              serr.setLength(0);
              serr.append(serri).append(" invalid equinox");
            }
//          goto return_err;
            fp.close(); return SweConst.ERR;
          } else {
//        *tequ = atof(sp);
            tequ.val = SwissLib.atof(sp);
          }
        }
        /* mean anomaly t0 */
        if (mano != null) {
          retc = check_t_terms(tt, cpos[2], mano);
          mano.val = sl.swe_degnorm(mano.val);
          if (retc == SweConst.ERR) {
            if (serr != null) {
              serr.append(serri).append(" mean anomaly value invalid");
            }
//          goto return_err;
            fp.close(); return SweConst.ERR;
          }
          /* if mean anomaly has t terms (which happens with fictitious
           * planet Vulcan), we set
           * epoch = tjd, so that no motion will be added anymore
           * equinox = tjd */
          if (retc == 1) {
            tjd0.val = tjd;
          }
          mano.val *= SwissData.DEGTORAD;
        }
        /* semi-axis */
        if (sema != null) {
          retc = check_t_terms(tt, cpos[3], sema);
          if (sema.val <= 0 || retc == SweConst.ERR) {
            if (serr != null) {
              serr.append(serri).append(" semi-axis value invalid");
            }
//          goto return_err;
            fp.close(); return SweConst.ERR;
          }
        }
        /* eccentricity */
        if (ecce != null) {
          retc = check_t_terms(tt, cpos[4], ecce);
          if (ecce.val >= 1 || ecce.val < 0 || retc == SweConst.ERR) {
            if (serr != null) {
              serr.setLength(0);
              serr.append(serri).append(" eccentricity invalid (no parabolic or hyperbolic or bits allowed)");
            }
//          goto return_err;
            fp.close(); return SweConst.ERR;
          }
        }
        /* perihelion argument */
        if (parg != null) {
          retc = check_t_terms(tt, cpos[5], parg);
          parg.val = sl.swe_degnorm(parg.val);
          if (retc == SweConst.ERR) {
            if (serr != null) {
              serr.setLength(0);
              serr.append(serri).append(" perihelion argument value invalid");
            }
//          goto return_err;
            fp.close(); return SweConst.ERR;
          }
          parg.val *= SwissData.DEGTORAD;
        }
        /* node */
        if (node != null) {
          retc = check_t_terms(tt, cpos[6], node);
          node.val = sl.swe_degnorm(node.val);
          if (retc == SweConst.ERR) {
            if (serr != null) {
              serr.setLength(0);
              serr.append(serri).append(" node value invalid");
            }
//          goto return_err;
            fp.close(); return SweConst.ERR;
          }
          node.val *= SwissData.DEGTORAD;
        }
        /* inclination */
        if (incl != null) {
          retc = check_t_terms(tt, cpos[7], incl);
          incl.val = sl.swe_degnorm(incl.val);
          if (retc == SweConst.ERR) {
            if (serr != null) {
              serr.setLength(0);
              serr.append(serri).append(" inclination value invalid");
            }
//          goto return_err;
            fp.close(); return SweConst.ERR;
          }
          incl.val *= SwissData.DEGTORAD;
        }
        /* planet name */
        if (pname != null) {
          sp = cpos[8];
          spIdx=0;
          while(sp.charAt(spIdx) == ' ' || sp.charAt(spIdx) == '\t')
            spIdx++;
          sp=sp.substring(spIdx);
//      swi_right_trim(sp);
          sp=sp.trim();
          pname.setLength(0); pname.append(sp);
        }
        /* geocentric */
        if (fict_ifl != null && ncpos > 9) {
//          for (sp = cpos[9]; *sp != '\0'; sp++)
//            *sp = tolower(*sp);
          sp = sp.substring(0,SMath.min(sp.length(),spIdx+9)) +
               sp.substring(SMath.min(sp.length(),spIdx+9)).toLowerCase();
//          if (strstr(cpos[9], "geo") != NULL)
//            fict_ifl.val |= FICT_GEO;
          if (cpos[9].indexOf("geo") >= 0) {
            fict_ifl.val |= FICT_GEO;
          }
        }
        break;
      }
      if (!elem_found) {
        if (serr != null) {
          serr.append(serri).append(" elements for planet ").append(ipl).append(" not found");
        }
//      goto return_err;
        fp.close(); return SweConst.ERR;
      }
      fp.close();
      return SweConst.OK;
    } catch (java.io.IOException e) {
      if (fp!=null) { try { fp.close(); } catch (java.io.IOException ie) { } }
    }
    return SweConst.ERR;
  }

  private int check_t_terms(double t, String sinp, DblObj doutp) {
    int i, isgn = 1, z;
    int retc = 0;
    int spidx;
    double tt[]=new double[5], fac;
    tt[0] = t / 36525;
    tt[1] = tt[0];
    tt[2] = tt[1] * tt[1];
    tt[3] = tt[2] * tt[1];
    tt[4] = tt[3] * tt[1];
//    if (strpbrk(sinp, "+-") != null)
    if (sinp.indexOf('+') + sinp.indexOf('-') > -2) {
      retc = 1; /* with additional terms */
    }
    spidx=0;
    doutp.val = 0;
    fac = 1;
    z = 0;
    while (true) {
      while(spidx<sinp.length() &&
            (sinp.charAt(spidx)==' ' || sinp.charAt(spidx)=='\t')) {
        spidx++;
      }
      if (spidx==sinp.length() ||
          sinp.charAt(spidx)=='+' || sinp.charAt(spidx)=='-') {
        if (z > 0) {
          doutp.val += fac;
        }
        isgn = 1;
        if (spidx!=sinp.length() && sinp.charAt(spidx) == '-') {
          isgn = -1;
        }
        fac = 1 * isgn;
        if (spidx==sinp.length()) {
          return retc;
        }
        spidx++;
      } else {
        while(spidx<sinp.length() &&
              (sinp.charAt(spidx)=='*' || sinp.charAt(spidx)==' '
              || sinp.charAt(spidx)=='\t')) {
          spidx++;
        }
        if (spidx<sinp.length() &&
            (sinp.charAt(spidx)=='t' || sinp.charAt(spidx)=='T')) {
                /* a T */
          spidx++;
          if (spidx<sinp.length() &&
              (sinp.charAt(spidx)=='+' || sinp.charAt(spidx)=='-')) {
            fac *= tt[0];
          } else if ((i = SwissLib.atoi(sinp.substring(SMath.min(sinp.length(),spidx)))) <= 4 && i >= 0) {
            fac *= tt[i];
          }
        } else {
          /* a number */
          double db=SwissLib.atof(sinp.substring(spidx));
          if (db!=0 || sinp.charAt(spidx)=='0') {
            fac *= db;
          }
        }
        while (spidx<sinp.length() &&
               (Character.isDigit(sinp.charAt(spidx)) ||
                sinp.charAt(spidx)=='.'))
          spidx++;
      }
      z++;
    }
  }

}
