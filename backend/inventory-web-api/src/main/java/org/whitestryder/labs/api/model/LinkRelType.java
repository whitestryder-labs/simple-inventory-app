package org.whitestryder.labs.api.model;

public enum LinkRelType {

	  // These link relation types are a subset of the IANA link relation types,
	  // See http://www.iana.org/assignments/link-relations/link-relations.xhtml
	  SELF(
	      "self",
	      "Identifies a resource equivalent to the containing element"),
	  EDIT(
	      "edit",
	      "Refers to a resource that can be used to edit the link's context"),
	  COLLECTION(
	      "collection",
	      "When included in a resource that represents a member of a collection, the 'collection' link relation identifies a target resource that represents a collection of which the context resource is a member"),
	  CREATE_FORM(
	      "create-form",
	      "Indicates a target resource that represents a form that can be used to append a new member to the link's context"),
	  ITEM(
	      "item",
	      "The target IRI points to a resource that is a member of the collection represented by the context IRI"),
	  RELATED(
	      "related",
	      "The target IRI points to a resource that is related to the link's content."),
	  CURRENT(
	          "current",
	          "Refers to a resource containing the most recent item(s) in a collection of resources."),
	  DESCRIBED_BY(
	      "describedby",
	      "Refers to a resource providing information about the link's context."),
	  DESCRIBES(
	      "describes",
	      "This link relation type is the inverse of the 'describedby' relation type. While 'describedby' establishes a relation from the described resource back to the resource that describes it, 'describes' established a relation from the describing resource to the resource it describes. If B is 'describedby' A, then A 'describes' B."),
	  X_BUY(
	  		"x-buy",
	  		"Non-standard identification of a 'purchase' resource for buying an item"),
	  X_AUTH_REQUIRED(
	  		"x-auth-required",
	  		"Non-standard identification resource that requires authentication to access"),
	  X_AUTH_LOGIN(
		  		"x-auth-login",
		  		"Non-standard identification resource that represents an authentication endpoint for login purposes"),
	 ;

	  
	  private final String text;

	  private final String code;

	  /**
	   * Constructor
	   * 
	   * @param code
	   * @param text
	   */
	  LinkRelType(String code, String text) {

	    this.code = code;
	    this.text = text;
	  }

	  /**
	   * text getter
	   *
	   * @return String
	   */
	  public String getText() {
	    return text;
	  }

	  /**
	   * DestCode getter
	   *
	   * @return String
	   */
	  public String getCode() {
	    return code;
	  }

	  /*
	   * (non-Javadoc)
	   * 
	   * @see java.lang.Enum#toString()
	   */
	  /**
	   * <p>toString.</p>
	   *
	   * @return a {@link java.lang.String} object.
	   */
	  public String toString() {
	    return code;
	  }

	  /**
	   * Allows for properly formatted list of link relation types.
	   *
	   * @param otherRelTypes a {@link gs.commons.inf.iadp.model.LinkRelType} object.
	   * @return String
	   */
	  public String combineWith(LinkRelType... otherRelTypes) {
	    String combinedCode = this.getCode();
	    if (otherRelTypes != null && otherRelTypes.length > 0) {
	      for (int i = 0; i < otherRelTypes.length; i++) {
	        combinedCode += " " + otherRelTypes[i];
	      }
	    }
	    return combinedCode;
	  }
	}
