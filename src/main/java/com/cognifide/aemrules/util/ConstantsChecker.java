package com.cognifide.aemrules.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConstantsChecker {

	private static final Map<String, String> CONSTANTS;

	private static final Map<String, String> ANNOTATION_CONSTANTS;

	static {
		Map<String, String> constants = new HashMap<>();

		// interface com.day.cq.commons.jcr.JcrConstants
		constants.put("jcr:autoCreated", "interface com.day.cq.commons.jcr.JcrConstants.JCR_AUTOCREATED");
		constants.put("jcr:baseVersion", "interface com.day.cq.commons.jcr.JcrConstants.JCR_BASEVERSION");
		constants.put("jcr:child", "interface com.day.cq.commons.jcr.JcrConstants.JCR_CHILD");
		constants.put("jcr:childNodeDefinition", "interface com.day.cq.commons.jcr.JcrConstants.JCR_CHILDNODEDEFINITION");
		constants.put("jcr:content", "interface com.day.cq.commons.jcr.JcrConstants.JCR_CONTENT");
		constants.put("jcr:created", "interface com.day.cq.commons.jcr.JcrConstants.JCR_CREATED");
		constants.put("jcr:data", "interface com.day.cq.commons.jcr.JcrConstants.JCR_DATA");
		constants.put("jcr:defaultPrimaryType", "interface com.day.cq.commons.jcr.JcrConstants.JCR_DEFAULTPRIMARYTYPE");
		constants.put("jcr:defaultValues", "interface com.day.cq.commons.jcr.JcrConstants.JCR_DEFAULTVALUES");
		constants.put("jcr:encoding", "interface com.day.cq.commons.jcr.JcrConstants.JCR_ENCODING");
		constants.put("jcr:frozenMixinTypes", "interface com.day.cq.commons.jcr.JcrConstants.JCR_FROZENMIXINTYPES");
		constants.put("jcr:frozenNode", "interface com.day.cq.commons.jcr.JcrConstants.JCR_FROZENNODE");
		constants.put("jcr:frozenPrimaryType", "interface com.day.cq.commons.jcr.JcrConstants.JCR_FROZENPRIMARYTYPE");
		constants.put("jcr:frozenUuid", "interface com.day.cq.commons.jcr.JcrConstants.JCR_FROZENUUID");
		constants.put("jcr:hasOrderableChildNodes", "interface com.day.cq.commons.jcr.JcrConstants.JCR_HASORDERABLECHILDNODES");
		constants.put("jcr:isCheckedOut", "interface com.day.cq.commons.jcr.JcrConstants.JCR_ISCHECKEDOUT");
		constants.put("jcr:isMixin", "interface com.day.cq.commons.jcr.JcrConstants.JCR_ISMIXIN");
		constants.put("jcr:language", "interface com.day.cq.commons.jcr.JcrConstants.JCR_LANGUAGE");
		constants.put("jcr:lastModified", "interface com.day.cq.commons.jcr.JcrConstants.JCR_LASTMODIFIED");
		constants.put("jcr:lockIsDeep", "interface com.day.cq.commons.jcr.JcrConstants.JCR_LOCKISDEEP");
		constants.put("jcr:lockOwner", "interface com.day.cq.commons.jcr.JcrConstants.JCR_LOCKOWNER");
		constants.put("jcr:mandatory", "interface com.day.cq.commons.jcr.JcrConstants.JCR_MANDATORY");
		constants.put("jcr:mergeFailed", "interface com.day.cq.commons.jcr.JcrConstants.JCR_MERGEFAILED");
		constants.put("jcr:mimeType", "interface com.day.cq.commons.jcr.JcrConstants.JCR_MIMETYPE");
		constants.put("jcr:mixinTypes", "interface com.day.cq.commons.jcr.JcrConstants.JCR_MIXINTYPES");
		constants.put("jcr:multiple", "interface com.day.cq.commons.jcr.JcrConstants.JCR_MULTIPLE");
		constants.put("jcr:name", "interface com.day.cq.commons.jcr.JcrConstants.JCR_NAME");
		constants.put("jcr:nodeTypeName", "interface com.day.cq.commons.jcr.JcrConstants.JCR_NODETYPENAME");
		constants.put("jcr:onParentVersion", "interface com.day.cq.commons.jcr.JcrConstants.JCR_ONPARENTVERSION");
		constants.put("jcr:predecessors", "interface com.day.cq.commons.jcr.JcrConstants.JCR_PREDECESSORS");
		constants.put("jcr:primaryItemName", "interface com.day.cq.commons.jcr.JcrConstants.JCR_PRIMARYITEMNAME");
		constants.put("jcr:primaryType", "interface com.day.cq.commons.jcr.JcrConstants.JCR_PRIMARYTYPE");
		constants.put("jcr:propertyDefinition", "interface com.day.cq.commons.jcr.JcrConstants.JCR_PROPERTYDEFINITION");
		constants.put("jcr:protected", "interface com.day.cq.commons.jcr.JcrConstants.JCR_PROTECTED");
		constants.put("jcr:requiredPrimaryTypes", "interface com.day.cq.commons.jcr.JcrConstants.JCR_REQUIREDPRIMARYTYPES");
		constants.put("jcr:requiredType", "interface com.day.cq.commons.jcr.JcrConstants.JCR_REQUIREDTYPE");
		constants.put("jcr:rootVersion", "interface com.day.cq.commons.jcr.JcrConstants.JCR_ROOTVERSION");
		constants.put("jcr:sameNameSiblings", "interface com.day.cq.commons.jcr.JcrConstants.JCR_SAMENAMESIBLINGS");
		constants.put("jcr:statement", "interface com.day.cq.commons.jcr.JcrConstants.JCR_STATEMENT");
		constants.put("jcr:successors", "interface com.day.cq.commons.jcr.JcrConstants.JCR_SUCCESSORS");
		constants.put("jcr:supertypes", "interface com.day.cq.commons.jcr.JcrConstants.JCR_SUPERTYPES");
		constants.put("jcr:system", "interface com.day.cq.commons.jcr.JcrConstants.JCR_SYSTEM");
		constants.put("jcr:uuid", "interface com.day.cq.commons.jcr.JcrConstants.JCR_UUID");
		constants.put("jcr:valueConstraints", "interface com.day.cq.commons.jcr.JcrConstants.JCR_VALUECONSTRAINTS");
		constants.put("jcr:versionHistory", "interface com.day.cq.commons.jcr.JcrConstants.JCR_VERSIONHISTORY");
		constants.put("jcr:versionLabels", "interface com.day.cq.commons.jcr.JcrConstants.JCR_VERSIONLABELS");
		constants.put("jcr:versionStorage", "interface com.day.cq.commons.jcr.JcrConstants.JCR_VERSIONSTORAGE");
		constants.put("jcr:versionableUuid", "interface com.day.cq.commons.jcr.JcrConstants.JCR_VERSIONABLEUUID");
		constants.put("jcr:path", "interface com.day.cq.commons.jcr.JcrConstants.JCR_PATH");
		constants.put("jcr:score", "interface com.day.cq.commons.jcr.JcrConstants.JCR_SCORE");
		constants.put("mix:lockable", "interface com.day.cq.commons.jcr.JcrConstants.MIX_LOCKABLE");
		constants.put("mix:referenceable", "interface com.day.cq.commons.jcr.JcrConstants.MIX_REFERENCEABLE");
		constants.put("mix:versionable", "interface com.day.cq.commons.jcr.JcrConstants.MIX_VERSIONABLE");
		constants.put("nt:base", "interface com.day.cq.commons.jcr.JcrConstants.NT_BASE");
		constants.put("nt:childNodeDefinition", "interface com.day.cq.commons.jcr.JcrConstants.NT_CHILDNODEDEFINITION");
		constants.put("nt:file", "interface com.day.cq.commons.jcr.JcrConstants.NT_FILE");
		constants.put("nt:folder", "interface com.day.cq.commons.jcr.JcrConstants.NT_FOLDER");
		constants.put("nt:frozenNode", "interface com.day.cq.commons.jcr.JcrConstants.NT_FROZENNODE");
		constants.put("nt:hierarchyNode", "interface com.day.cq.commons.jcr.JcrConstants.NT_HIERARCHYNODE");
		constants.put("nt:linkedFile", "interface com.day.cq.commons.jcr.JcrConstants.NT_LINKEDFILE");
		constants.put("nt:nodeType", "interface com.day.cq.commons.jcr.JcrConstants.NT_NODETYPE");
		constants.put("nt:propertyDefinition", "interface com.day.cq.commons.jcr.JcrConstants.NT_PROPERTYDEFINITION");
		constants.put("nt:query", "interface com.day.cq.commons.jcr.JcrConstants.NT_QUERY");
		constants.put("nt:resource", "interface com.day.cq.commons.jcr.JcrConstants.NT_RESOURCE");
		constants.put("nt:unstructured", "interface com.day.cq.commons.jcr.JcrConstants.NT_UNSTRUCTURED");
		constants.put("nt:version", "interface com.day.cq.commons.jcr.JcrConstants.NT_VERSION");
		constants.put("nt:versionHistory", "interface com.day.cq.commons.jcr.JcrConstants.NT_VERSIONHISTORY");
		constants.put("nt:versionLabels", "interface com.day.cq.commons.jcr.JcrConstants.NT_VERSIONLABELS");
		constants.put("nt:versionedChild", "interface com.day.cq.commons.jcr.JcrConstants.NT_VERSIONEDCHILD");
		constants.put("jcr:title", "interface com.day.cq.commons.jcr.JcrConstants.JCR_TITLE");
		constants.put("jcr:description", "interface com.day.cq.commons.jcr.JcrConstants.JCR_DESCRIPTION");
		constants.put("jcr:createdBy", "interface com.day.cq.commons.jcr.JcrConstants.JCR_CREATED_BY");
		constants.put("jcr:lastModifiedBy", "interface com.day.cq.commons.jcr.JcrConstants.JCR_LAST_MODIFIED_BY");
		constants.put("mix:title", "interface com.day.cq.commons.jcr.JcrConstants.MIX_TITLE");
		constants.put("mix:created", "interface com.day.cq.commons.jcr.JcrConstants.MIX_CREATED");
		constants.put("mix:lastModified", "interface com.day.cq.commons.jcr.JcrConstants.MIX_LAST_MODIFIED");

		// interface com.day.cq.dam.api.DamConstants
		constants.put("renditions", "interface com.day.cq.dam.api.DamConstants.RENDITIONS_FOLDER");
		constants.put("dam:Asset", "interface com.day.cq.dam.api.DamConstants.NT_DAM_ASSET");
		constants.put("dam:AssetContent", "interface com.day.cq.dam.api.DamConstants.NT_DAM_ASSETCONTENT");
		constants.put("dam:extracted", "interface com.day.cq.dam.api.DamConstants.PN_EXTRACTED");
		constants.put("dam:sha1", "interface com.day.cq.dam.api.DamConstants.PN_SHA1");
		constants.put("dam:size", "interface com.day.cq.dam.api.DamConstants.DAM_SIZE");
		constants.put("cq:name", "interface com.day.cq.dam.api.DamConstants.PN_NAME");
		constants.put("cq:versionCreator", "interface com.day.cq.dam.api.DamConstants.PN_VERSION_CREATOR");
		constants.put("dc:contributor", "interface com.day.cq.dam.api.DamConstants.DC_CONTRIBUTOR");
		constants.put("dc:coverage", "interface com.day.cq.dam.api.DamConstants.DC_COVERAGE");
		constants.put("dc:creator", "interface com.day.cq.dam.api.DamConstants.DC_CREATOR");
		constants.put("dc:date", "interface com.day.cq.dam.api.DamConstants.DC_DATE");
		constants.put("dc:description", "interface com.day.cq.dam.api.DamConstants.DC_DESCRIPTION");
		constants.put("dc:extent", "interface com.day.cq.dam.api.DamConstants.DC_EXTENT");
		constants.put("dc:format", "interface com.day.cq.dam.api.DamConstants.DC_FORMAT");
		constants.put("dc:identifier", "interface com.day.cq.dam.api.DamConstants.DC_IDENTIFIER");
		constants.put("dc:language", "interface com.day.cq.dam.api.DamConstants.DC_LANGUAGE");
		constants.put("dc:modified", "interface com.day.cq.dam.api.DamConstants.DC_MODIFIED");
		constants.put("dc:publisher", "interface com.day.cq.dam.api.DamConstants.DC_PUBLISHER");
		constants.put("dc:relation", "interface com.day.cq.dam.api.DamConstants.DC_RELATION");
		constants.put("dc:rights", "interface com.day.cq.dam.api.DamConstants.DC_RIGHTS");
		constants.put("dc:subject", "interface com.day.cq.dam.api.DamConstants.DC_SUBJECT");
		constants.put("dc:title", "interface com.day.cq.dam.api.DamConstants.DC_TITLE");
		constants.put("dc:type", "interface com.day.cq.dam.api.DamConstants.DC_TYPE");
		constants.put("cq5dam.thumbnail", "interface com.day.cq.dam.api.DamConstants.PREFIX_ASSET_THUMBNAIL");
		constants.put("exif:PixelXDimension", "interface com.day.cq.dam.api.DamConstants.EXIF_PIXELXDIMENSION");
		constants.put("exif:PixelYDimension", "interface com.day.cq.dam.api.DamConstants.EXIF_PIXELYDIMENSION");
		constants.put("tiff:ImageLength", "interface com.day.cq.dam.api.DamConstants.TIFF_IMAGELENGTH");
		constants.put("tiff:ImageWidth", "interface com.day.cq.dam.api.DamConstants.TIFF_IMAGEWIDTH");
		constants.put("assetExpired", "interface com.day.cq.dam.api.DamConstants.ACTIVITY_TYPE_ASSET_EXPIRED");
		constants.put("assetExpiring", "interface com.day.cq.dam.api.DamConstants.ACTIVITY_TYPE_ASSET_EXPIRING");
		constants.put("dam:lastPostExpirationRun", "interface com.day.cq.dam.api.DamConstants.LAST_EXPIRY_NOTIFICATION_PROPNAME");
		constants.put("dam/collection", "interface com.day.cq.dam.api.DamConstants.COLLECTION_SLING_RES_TYPE");
		constants.put("dam/smartcollection", "interface com.day.cq.dam.api.DamConstants.SMART_COLLECTION_SLING_RES_TYPE");
		constants.put("dam/content/schemaeditors/forms", "interface com.day.cq.dam.api.DamConstants.SCHEMA_EDITOR_FORMS_BASE_DIR");
		constants.put("processingProfile", "interface com.day.cq.dam.api.DamConstants.PROCESSING_PROFILE");
		constants.put("metadataProfile", "interface com.day.cq.dam.api.DamConstants.METADATA_PROFILE");
		constants.put("videoProfile", "interface com.day.cq.dam.api.DamConstants.VIDEO_PROFILE");
		constants.put("imageProfile", "interface com.day.cq.dam.api.DamConstants.IMAGE_PROFILE");
		constants.put("folderThumbnail", "interface com.day.cq.dam.api.DamConstants.THUMBNAIL_NODE");
		constants.put("downloadUrl", "interface com.day.cq.dam.api.DamConstants.DOWNLOAD_URL");

		// interface com.day.cq.wcm.api.NameConstants
		constants.put("cq:Page", "interface com.day.cq.wcm.api.NameConstants.NT_PAGE");
		constants.put("cq:PseudoPage", "interface com.day.cq.wcm.api.NameConstants.NT_PSEUDO_PAGE");
		constants.put("cq:Template", "interface com.day.cq.wcm.api.NameConstants.NT_TEMPLATE");
		constants.put("cq:Component", "interface com.day.cq.wcm.api.NameConstants.NT_COMPONENT");
		constants.put("cq:EditConfig", "interface com.day.cq.wcm.api.NameConstants.NT_EDIT_CONFIG");
		constants.put("dialog", "interface com.day.cq.wcm.api.NameConstants.NN_DIALOG");
		constants.put("dialogPath", "interface com.day.cq.wcm.api.NameConstants.PN_DIALOG_PATH");
		constants.put("design_dialog", "interface com.day.cq.wcm.api.NameConstants.NN_DESIGN_DIALOG");
		constants.put("designDialogPath", "interface com.day.cq.wcm.api.NameConstants.PN_DESIGN_DIALOG_PATH");
		constants.put("cq:editConfig", "interface com.day.cq.wcm.api.NameConstants.NN_EDIT_CONFIG");
		constants.put("cq:childEditConfig", "interface com.day.cq.wcm.api.NameConstants.NN_CHILD_EDIT_CONFIG");
		constants.put("icon.png", "interface com.day.cq.wcm.api.NameConstants.NN_ICON_PNG");
		constants.put("thumbnail.png", "interface com.day.cq.wcm.api.NameConstants.NN_THUMBNAIL_PNG");
		constants.put("cq:cellName", "interface com.day.cq.wcm.api.NameConstants.PN_CELL_NAME");
		constants.put("cq:isContainer", "interface com.day.cq.wcm.api.NameConstants.PN_IS_CONTAINER");
		constants.put("cq:noDecoration", "interface com.day.cq.wcm.api.NameConstants.PN_NO_DECORATION");
		constants.put("cq:htmlTag", "interface com.day.cq.wcm.api.NameConstants.NN_HTML_TAG");
		constants.put("allowedPaths", "interface com.day.cq.wcm.api.NameConstants.PN_ALLOWED_PATHS");
		constants.put("allowedChildren", "interface com.day.cq.wcm.api.NameConstants.PN_ALLOWED_CHILDREN");
		constants.put("allowedParents", "interface com.day.cq.wcm.api.NameConstants.PN_ALLOWED_PARENTS");
		constants.put("componentGroup", "interface com.day.cq.wcm.api.NameConstants.PN_COMPONENT_GROUP");
		constants.put("ranking", "interface com.day.cq.wcm.api.NameConstants.PN_RANKING");
		constants.put("sitePath", "interface com.day.cq.wcm.api.NameConstants.PN_SITE_PATH");
		constants.put("params", "interface com.day.cq.wcm.api.NameConstants.NN_PARAMS");
		constants.put("virtual", "interface com.day.cq.wcm.api.NameConstants.NN_VIRTUAL");
		constants.put("cq:templatePath", "interface com.day.cq.wcm.api.NameConstants.PN_TEMPLATE_PATH");
		constants.put("cq:template", "interface com.day.cq.wcm.api.NameConstants.NN_TEMPLATE");
		constants.put("cq:tagName", "interface com.day.cq.wcm.api.NameConstants.PN_TAG_NAME");
		constants.put("cq:infoProviders", "interface com.day.cq.wcm.api.NameConstants.NN_INFO_PROVIDERS");
		constants.put("className", "interface com.day.cq.wcm.api.NameConstants.PN_CLASS_NAME");
		constants.put("cq:disableTargeting", "interface com.day.cq.wcm.api.NameConstants.PN_DISABLE_TARGETING");
		constants.put("cq:layout", "interface com.day.cq.wcm.api.NameConstants.PN_LAYOUT");
		constants.put("cq:dialogMode", "interface com.day.cq.wcm.api.NameConstants.PN_DIALOG_MODE");
		constants.put("cq:inplaceEditing", "interface com.day.cq.wcm.api.NameConstants.NN_INPLACE_EDITING");
		constants.put("cq:emptyText", "interface com.day.cq.wcm.api.NameConstants.PN_EMPTY_TEXT");
		constants.put("cq:actions", "interface com.day.cq.wcm.api.NameConstants.PN_ACTIONS");
		constants.put("cq:actionConfigs", "interface com.day.cq.wcm.api.NameConstants.NN_ACTION_CONFIGS");
		constants.put("cq:formParameters", "interface com.day.cq.wcm.api.NameConstants.NN_FORM_PARAMETERS");
		constants.put("cq:dropTargets", "interface com.day.cq.wcm.api.NameConstants.NN_DROP_TARGETS");
		constants.put("cq:listeners", "interface com.day.cq.wcm.api.NameConstants.NN_LISTENERS");
		constants.put("propertyName", "interface com.day.cq.wcm.api.NameConstants.PN_DT_NAME");
		constants.put("accept", "interface com.day.cq.wcm.api.NameConstants.PN_DT_ACCEPT");
		constants.put("groups", "interface com.day.cq.wcm.api.NameConstants.PN_DT_GROUPS");
		constants.put("cq:inherit", "interface com.day.cq.wcm.api.NameConstants.PN_INHERIT");
		constants.put("cq:designPath", "interface com.day.cq.wcm.api.NameConstants.PN_DESIGN_PATH");
		constants.put("cq:parentPath", "interface com.day.cq.wcm.api.NameConstants.PN_PARENT_PATH");
		constants.put("cq:name", "interface com.day.cq.wcm.api.NameConstants.PN_NAME");
		constants.put("cq:childrenOrder", "interface com.day.cq.wcm.api.NameConstants.PN_CHILDREN_ORDER");
		constants.put("cq:siblingOrder", "interface com.day.cq.wcm.api.NameConstants.PN_SIBLING_ORDER");
		constants.put("cq:versionComment", "interface com.day.cq.wcm.api.NameConstants.PN_VERSION_COMMENT");
		constants.put("shortTitle", "interface com.day.cq.wcm.api.NameConstants.PN_SHORT_TITLE");
		constants.put("pageTitle", "interface com.day.cq.wcm.api.NameConstants.PN_PAGE_TITLE");
		constants.put("navTitle", "interface com.day.cq.wcm.api.NameConstants.PN_NAV_TITLE");
		constants.put("hideInNav", "interface com.day.cq.wcm.api.NameConstants.PN_HIDE_IN_NAV");
		constants.put("onTime", "interface com.day.cq.wcm.api.NameConstants.PN_ON_TIME");
		constants.put("offTime", "interface com.day.cq.wcm.api.NameConstants.PN_OFF_TIME");
		constants.put("cq:lastModified", "interface com.day.cq.wcm.api.NameConstants.PN_PAGE_LAST_MOD");
		constants.put("cq:lastModifiedBy", "interface com.day.cq.wcm.api.NameConstants.PN_PAGE_LAST_MOD_BY");
		constants.put("cq:lastReplicated", "interface com.day.cq.wcm.api.NameConstants.PN_PAGE_LAST_REPLICATED");
		constants.put("cq:lastReplicatedBy", "interface com.day.cq.wcm.api.NameConstants.PN_PAGE_LAST_REPLICATED_BY");
		constants.put("cq:lastReplicationAction", "interface com.day.cq.wcm.api.NameConstants.PN_PAGE_LAST_REPLICATION_ACTION");
		constants.put("cq:tags", "interface com.day.cq.wcm.api.NameConstants.PN_TAGS");
		constants.put("cq:defaultView", "interface com.day.cq.wcm.api.NameConstants.PN_DEFAULT_VIEW");
		constants.put("sling:vanityPath", "interface com.day.cq.wcm.api.NameConstants.PN_SLING_VANITY_PATH");
		constants.put("cq:allowedTemplates", "interface com.day.cq.wcm.api.NameConstants.PN_ALLOWED_TEMPLATES");
		constants.put("vanityUrl", "interface com.day.cq.wcm.api.NameConstants.PN_VANITY_URL");
		constants.put("cq:lastPublished", "interface com.day.cq.wcm.api.NameConstants.PN_PAGE_LAST_PUBLISHED");
		constants.put("cq:lastPublishedBy", "interface com.day.cq.wcm.api.NameConstants.PN_PAGE_LAST_PUBLISHED_BY");
		constants.put("cq:lastReplicationStatus", "interface com.day.cq.wcm.api.NameConstants.PN_PAGE_LAST_REPLICATION_STATUS");

		// interface com.day.cq.wcm.webservicesupport.ConfigurationConstants
		constants.put("cq/cloudserviceconfigs/components/servicepage", "interface com.day.cq.wcm.webservicesupport.ConfigurationConstants.RT_SERVICE");
		constants.put("cq/cloudserviceconfigs/components/configpage", "interface com.day.cq.wcm.webservicesupport.ConfigurationConstants.RT_CONFIGURATION");
		constants.put("cq:cloudserviceconfigs", "interface com.day.cq.wcm.webservicesupport.ConfigurationConstants.PN_CONFIGURATIONS");

		// class org.apache.sling.jcr.resource.JcrResourceConstants
		constants.put("http://sling.apache.org/jcr/sling/1.0", "class org.apache.sling.jcr.resource.JcrResourceConstants.SLING_NAMESPACE_URI");
		constants.put("sling:resourceType", "class org.apache.sling.jcr.resource.JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY");
		constants.put("sling:resourceSuperType", "class org.apache.sling.jcr.resource.JcrResourceConstants.SLING_RESOURCE_SUPER_TYPE_PROPERTY");
		constants.put("user.jcr.credentials", "class org.apache.sling.jcr.resource.JcrResourceConstants.AUTHENTICATION_INFO_CREDENTIALS");
		constants.put("user.jcr.workspace", "class org.apache.sling.jcr.resource.JcrResourceConstants.AUTHENTICATION_INFO_WORKSPACE");
		constants.put("user.jcr.session", "class org.apache.sling.jcr.resource.JcrResourceConstants.AUTHENTICATION_INFO_SESSION");
		constants.put("sling:Folder", "class org.apache.sling.jcr.resource.JcrResourceConstants.NT_SLING_FOLDER");
		constants.put("sling:OrderedFolder", "class org.apache.sling.jcr.resource.JcrResourceConstants.NT_SLING_ORDERED_FOLDER");

		// interface com.day.cq.tagging.TagConstants
		constants.put("cq:Tag", "interface com.day.cq.tagging.TagConstants.NT_TAG");
		constants.put("cq:Taggable", "interface com.day.cq.tagging.TagConstants.NT_TAGGABLE");
		constants.put("cq:tags", "interface com.day.cq.tagging.TagConstants.PN_TAGS");
		constants.put("cq:movedTo", "interface com.day.cq.tagging.TagConstants.PN_MOVED_TO");
		constants.put("cq:backlinks", "interface com.day.cq.tagging.TagConstants.PN_BACKLINKS");

		CONSTANTS = Collections.unmodifiableMap(constants);

		Map<String, String> annotationConstants = new HashMap<>();

		// class org.apache.sling.api.SlingConstants
		annotationConstants.put("org.apache.sling.api.include.servlet", "class org.apache.sling.api.SlingConstants.ATTR_REQUEST_SERVLET");
		annotationConstants.put("org.apache.sling.api.include.resource", "class org.apache.sling.api.SlingConstants.ATTR_REQUEST_CONTENT");
		annotationConstants.put("org.apache.sling.api.include.request_path_info", "class org.apache.sling.api.SlingConstants.ATTR_REQUEST_PATH_INFO");
		annotationConstants.put("javax.servlet.include.request_uri", "class org.apache.sling.api.SlingConstants.ATTR_INCLUDE_REQUEST_URI");
		annotationConstants.put("javax.servlet.include.context_path", "class org.apache.sling.api.SlingConstants.ATTR_INCLUDE_CONTEXT_PATH");
		annotationConstants.put("javax.servlet.include.servlet_path", "class org.apache.sling.api.SlingConstants.ATTR_INCLUDE_SERVLET_PATH");
		annotationConstants.put("javax.servlet.include.path_info", "class org.apache.sling.api.SlingConstants.ATTR_INCLUDE_PATH_INFO");
		annotationConstants.put("javax.servlet.include.query_string", "class org.apache.sling.api.SlingConstants.ATTR_INCLUDE_QUERY_STRING");
		annotationConstants.put("javax.servlet.error.exception", "class org.apache.sling.api.SlingConstants.ERROR_EXCEPTION");
		annotationConstants.put("javax.servlet.error.exception_type", "class org.apache.sling.api.SlingConstants.ERROR_EXCEPTION_TYPE");
		annotationConstants.put("javax.servlet.error.message", "class org.apache.sling.api.SlingConstants.ERROR_MESSAGE");
		annotationConstants.put("javax.servlet.error.request_uri", "class org.apache.sling.api.SlingConstants.ERROR_REQUEST_URI");
		annotationConstants.put("javax.servlet.error.servlet_name", "class org.apache.sling.api.SlingConstants.ERROR_SERVLET_NAME");
		annotationConstants.put("javax.servlet.error.status_code", "class org.apache.sling.api.SlingConstants.ERROR_STATUS");
		annotationConstants.put("org/apache/sling/api/resource/Resource/ADDED", "class org.apache.sling.api.SlingConstants.TOPIC_RESOURCE_ADDED");
		annotationConstants.put("org/apache/sling/api/resource/Resource/REMOVED", "class org.apache.sling.api.SlingConstants.TOPIC_RESOURCE_REMOVED");
		annotationConstants.put("org/apache/sling/api/resource/Resource/CHANGED", "class org.apache.sling.api.SlingConstants.TOPIC_RESOURCE_CHANGED");
		annotationConstants.put("org/apache/sling/api/resource/ResourceProvider/ADDED", "class org.apache.sling.api.SlingConstants.TOPIC_RESOURCE_PROVIDER_ADDED");
		annotationConstants.put("org/apache/sling/api/resource/ResourceProvider/REMOVED", "class org.apache.sling.api.SlingConstants.TOPIC_RESOURCE_PROVIDER_REMOVED");
		annotationConstants.put("org/apache/sling/api/resource/ResourceResolverMapping/CHANGED", "class org.apache.sling.api.SlingConstants.TOPIC_RESOURCE_RESOLVER_MAPPING_CHANGED");
		annotationConstants.put("path", "class org.apache.sling.api.SlingConstants.PROPERTY_PATH");
		annotationConstants.put("userid", "class org.apache.sling.api.SlingConstants.PROPERTY_USERID");
		annotationConstants.put("resourceType", "class org.apache.sling.api.SlingConstants.PROPERTY_RESOURCE_TYPE");
		annotationConstants.put("resourceSuperType", "class org.apache.sling.api.SlingConstants.PROPERTY_RESOURCE_SUPER_TYPE");
		annotationConstants.put("resourceChangedAttributes", "class org.apache.sling.api.SlingConstants.PROPERTY_CHANGED_ATTRIBUTES");
		annotationConstants.put("resourceAddedAttributes", "class org.apache.sling.api.SlingConstants.PROPERTY_ADDED_ATTRIBUTES");
		annotationConstants.put("resourceRemovedAttributes", "class org.apache.sling.api.SlingConstants.PROPERTY_REMOVED_ATTRIBUTES");
		annotationConstants.put("org/apache/sling/api/adapter/AdapterFactory/ADDED", "class org.apache.sling.api.SlingConstants.TOPIC_ADAPTER_FACTORY_ADDED");
		annotationConstants.put("org/apache/sling/api/adapter/AdapterFactory/REMOVED", "class org.apache.sling.api.SlingConstants.TOPIC_ADAPTER_FACTORY_REMOVED");
		annotationConstants.put("adaptables", "class org.apache.sling.api.SlingConstants.PROPERTY_ADAPTABLE_CLASSES");
		annotationConstants.put("adapters", "class org.apache.sling.api.SlingConstants.PROPERTY_ADAPTER_CLASSES");
		annotationConstants.put("sling.core.current.servletName", "class org.apache.sling.api.SlingConstants.SLING_CURRENT_SERVLET_NAME");

		// class org.apache.sling.api.servlets.HttpConstants
		annotationConstants.put("OPTIONS", "class org.apache.sling.api.servlets.HttpConstants.METHOD_OPTIONS");
		annotationConstants.put("GET", "class org.apache.sling.api.servlets.HttpConstants.METHOD_GET");
		annotationConstants.put("HEAD", "class org.apache.sling.api.servlets.HttpConstants.METHOD_HEAD");
		annotationConstants.put("POST", "class org.apache.sling.api.servlets.HttpConstants.METHOD_POST");
		annotationConstants.put("PUT", "class org.apache.sling.api.servlets.HttpConstants.METHOD_PUT");
		annotationConstants.put("DELETE", "class org.apache.sling.api.servlets.HttpConstants.METHOD_DELETE");
		annotationConstants.put("TRACE", "class org.apache.sling.api.servlets.HttpConstants.METHOD_TRACE");
		annotationConstants.put("CONNECT", "class org.apache.sling.api.servlets.HttpConstants.METHOD_CONNECT");
		annotationConstants.put("Accept", "class org.apache.sling.api.servlets.HttpConstants.HEADER_ACCEPT");
		annotationConstants.put("ETag", "class org.apache.sling.api.servlets.HttpConstants.HEADER_ETAG");
		annotationConstants.put("If-Match", "class org.apache.sling.api.servlets.HttpConstants.HEADER_IF_MATCH");
		annotationConstants.put("If-Modified-Since", "class org.apache.sling.api.servlets.HttpConstants.HEADER_IF_MODIFIED_SINCE");
		annotationConstants.put("Last-Modified", "class org.apache.sling.api.servlets.HttpConstants.HEADER_LAST_MODIFIED");

		// class org.apache.sling.engine.EngineConstants
		annotationConstants.put("javax.servlet.Filter", "class org.apache.sling.engine.EngineConstants.FILTER_NAME");
		annotationConstants.put("sling.filter.scope", "class org.apache.sling.engine.EngineConstants.SLING_FILTER_SCOPE");
		annotationConstants.put("COMPONENT", "class org.apache.sling.engine.EngineConstants.FILTER_SCOPE_COMPONENT");
		annotationConstants.put("ERROR", "class org.apache.sling.engine.EngineConstants.FILTER_SCOPE_ERROR");
		annotationConstants.put("INCLUDE", "class org.apache.sling.engine.EngineConstants.FILTER_SCOPE_INCLUDE");
		annotationConstants.put("FORWARD", "class org.apache.sling.engine.EngineConstants.FILTER_SCOPE_FORWARD");
		annotationConstants.put("REQUEST", "class org.apache.sling.engine.EngineConstants.FILTER_SCOPE_REQUEST");

		ANNOTATION_CONSTANTS = Collections.unmodifiableMap(annotationConstants);
	}

	private ConstantsChecker() {
		// empty
	}

	public static boolean isAnnotationConstant(String value) {
		return ANNOTATION_CONSTANTS.containsKey(value);
	}

	public static String getAnnotationConstantFieldName(String value) {
		return ANNOTATION_CONSTANTS.get(value);
	}

	public static String getConstantFieldName(String value) {
		return CONSTANTS.get(value);
	}

	public static boolean isConstant(String value) {
		return CONSTANTS.containsKey(value);
	}

}
