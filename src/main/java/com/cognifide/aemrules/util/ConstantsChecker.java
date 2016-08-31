/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.cognifide.aemrules.util;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ConstantsChecker {

	private static final Map<String, String> CONSTANTS;

	private static final Map<String, String> ANNOTATION_CONSTANTS;

	static {
		CONSTANTS = ImmutableMap.<String, String>builder()

				// interface com.day.cq.commons.jcr.JcrConstants
				.put("jcr:autoCreated", "Use constant JCR_AUTOCREATED from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:baseVersion", "Use constant JCR_BASEVERSION from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:child", "Use constant JCR_CHILD from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:childNodeDefinition", "Use constant JCR_CHILDNODEDEFINITION from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:content", "Use constant JCR_CONTENT from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:data", "Use constant JCR_DATA from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:defaultPrimaryType", "Use constant JCR_DEFAULTPRIMARYTYPE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:defaultValues", "Use constant JCR_DEFAULTVALUES from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:encoding", "Use constant JCR_ENCODING from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:frozenMixinTypes", "Use constant JCR_FROZENMIXINTYPES from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:frozenNode", "Use constant JCR_FROZENNODE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:frozenPrimaryType", "Use constant JCR_FROZENPRIMARYTYPE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:frozenUuid", "Use constant JCR_FROZENUUID from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:hasOrderableChildNodes", "Use constant JCR_HASORDERABLECHILDNODES from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:isCheckedOut", "Use constant JCR_ISCHECKEDOUT from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:isMixin", "Use constant JCR_ISMIXIN from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:language", "Use constant JCR_LANGUAGE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:lockIsDeep", "Use constant JCR_LOCKISDEEP from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:lockOwner", "Use constant JCR_LOCKOWNER from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:mandatory", "Use constant JCR_MANDATORY from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:mergeFailed", "Use constant JCR_MERGEFAILED from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:mimeType", "Use constant JCR_MIMETYPE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:mixinTypes", "Use constant JCR_MIXINTYPES from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:multiple", "Use constant JCR_MULTIPLE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:name", "Use constant JCR_NAME from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:nodeTypeName", "Use constant JCR_NODETYPENAME from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:onParentVersion", "Use constant JCR_ONPARENTVERSION from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:predecessors", "Use constant JCR_PREDECESSORS from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:primaryItemName", "Use constant JCR_PRIMARYITEMNAME from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:primaryType", "Use constant JCR_PRIMARYTYPE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:propertyDefinition", "Use constant JCR_PROPERTYDEFINITION from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:protected", "Use constant JCR_PROTECTED from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:requiredPrimaryTypes", "Use constant JCR_REQUIREDPRIMARYTYPES from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:requiredType", "Use constant JCR_REQUIREDTYPE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:rootVersion", "Use constant JCR_ROOTVERSION from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:sameNameSiblings", "Use constant JCR_SAMENAMESIBLINGS from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:statement", "Use constant JCR_STATEMENT from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:successors", "Use constant JCR_SUCCESSORS from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:supertypes", "Use constant JCR_SUPERTYPES from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:system", "Use constant JCR_SYSTEM from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:uuid", "Use constant JCR_UUID from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:valueConstraints", "Use constant JCR_VALUECONSTRAINTS from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:versionHistory", "Use constant JCR_VERSIONHISTORY from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:versionLabels", "Use constant JCR_VERSIONLABELS from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:versionStorage", "Use constant JCR_VERSIONSTORAGE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:versionableUuid", "Use constant JCR_VERSIONABLEUUID from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:path", "Use constant JCR_PATH from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:score", "Use constant JCR_SCORE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("mix:lockable", "Use constant MIX_LOCKABLE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("mix:referenceable", "Use constant MIX_REFERENCEABLE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("mix:versionable", "Use constant MIX_VERSIONABLE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:base", "Use constant NT_BASE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:childNodeDefinition", "Use constant NT_CHILDNODEDEFINITION from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:file", "Use constant NT_FILE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:folder", "Use constant NT_FOLDER from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:frozenNode", "Use constant NT_FROZENNODE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:hierarchyNode", "Use constant NT_HIERARCHYNODE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:linkedFile", "Use constant NT_LINKEDFILE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:nodeType", "Use constant NT_NODETYPE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:propertyDefinition", "Use constant NT_PROPERTYDEFINITION from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:query", "Use constant NT_QUERY from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:resource", "Use constant NT_RESOURCE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:unstructured", "Use constant NT_UNSTRUCTURED from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:version", "Use constant NT_VERSION from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:versionHistory", "Use constant NT_VERSIONHISTORY from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:versionLabels", "Use constant NT_VERSIONLABELS from interface com.day.cq.commons.jcr.JcrConstants")
				.put("nt:versionedChild", "Use constant NT_VERSIONEDCHILD from interface com.day.cq.commons.jcr.JcrConstants")
				.put("jcr:title", "Use constant JCR_TITLE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("mix:title", "Use constant MIX_TITLE from interface com.day.cq.commons.jcr.JcrConstants")
				.put("mix:created", "Use constant MIX_CREATED from interface com.day.cq.commons.jcr.JcrConstants")
				.put("mix:lastModified", "Use constant MIX_LAST_MODIFIED from interface com.day.cq.commons.jcr.JcrConstants")

				// interface com.day.cq.dam.api.DamConstants
				.put("dam:Asset", "Use constant NT_DAM_ASSET from interface com.day.cq.dam.api.DamConstants")
				.put("dam:AssetContent", "Use constant NT_DAM_ASSETCONTENT from interface com.day.cq.dam.api.DamConstants")
				.put("dam:extracted", "Use constant PN_EXTRACTED from interface com.day.cq.dam.api.DamConstants")
				.put("dam:sha1", "Use constant PN_SHA1 from interface com.day.cq.dam.api.DamConstants")
				.put("dam:size", "Use constant DAM_SIZE from interface com.day.cq.dam.api.DamConstants")
				.put("cq:versionCreator", "Use constant PN_VERSION_CREATOR from interface com.day.cq.dam.api.DamConstants")
				.put("dc:contributor", "Use constant DC_CONTRIBUTOR from interface com.day.cq.dam.api.DamConstants")
				.put("dc:coverage", "Use constant DC_COVERAGE from interface com.day.cq.dam.api.DamConstants")
				.put("dc:creator", "Use constant DC_CREATOR from interface com.day.cq.dam.api.DamConstants")
				.put("dc:date", "Use constant DC_DATE from interface com.day.cq.dam.api.DamConstants")
				.put("dc:description", "Use constant DC_DESCRIPTION from interface com.day.cq.dam.api.DamConstants")
				.put("dc:extent", "Use constant DC_EXTENT from interface com.day.cq.dam.api.DamConstants")
				.put("dc:format", "Use constant DC_FORMAT from interface com.day.cq.dam.api.DamConstants")
				.put("dc:identifier", "Use constant DC_IDENTIFIER from interface com.day.cq.dam.api.DamConstants")
				.put("dc:language", "Use constant DC_LANGUAGE from interface com.day.cq.dam.api.DamConstants")
				.put("dc:modified", "Use constant DC_MODIFIED from interface com.day.cq.dam.api.DamConstants")
				.put("dc:publisher", "Use constant DC_PUBLISHER from interface com.day.cq.dam.api.DamConstants")
				.put("dc:relation", "Use constant DC_RELATION from interface com.day.cq.dam.api.DamConstants")
				.put("dc:rights", "Use constant DC_RIGHTS from interface com.day.cq.dam.api.DamConstants")
				.put("dc:subject", "Use constant DC_SUBJECT from interface com.day.cq.dam.api.DamConstants")
				.put("dc:title", "Use constant DC_TITLE from interface com.day.cq.dam.api.DamConstants")
				.put("dc:type", "Use constant DC_TYPE from interface com.day.cq.dam.api.DamConstants")
				.put("cq5dam.thumbnail", "Use constant PREFIX_ASSET_THUMBNAIL from interface com.day.cq.dam.api.DamConstants")
				.put("exif:PixelXDimension", "Use constant EXIF_PIXELXDIMENSION from interface com.day.cq.dam.api.DamConstants")
				.put("exif:PixelYDimension", "Use constant EXIF_PIXELYDIMENSION from interface com.day.cq.dam.api.DamConstants")
				.put("tiff:ImageLength", "Use constant TIFF_IMAGELENGTH from interface com.day.cq.dam.api.DamConstants")
				.put("tiff:ImageWidth", "Use constant TIFF_IMAGEWIDTH from interface com.day.cq.dam.api.DamConstants")
				.put("assetExpired", "Use constant ACTIVITY_TYPE_ASSET_EXPIRED from interface com.day.cq.dam.api.DamConstants")
				.put("assetExpiring", "Use constant ACTIVITY_TYPE_ASSET_EXPIRING from interface com.day.cq.dam.api.DamConstants")
				.put("dam:lastPostExpirationRun", "Use constant LAST_EXPIRY_NOTIFICATION_PROPNAME from interface com.day.cq.dam.api.DamConstants")
				.put("dam/collection", "Use constant COLLECTION_SLING_RES_TYPE from interface com.day.cq.dam.api.DamConstants")
				.put("dam/smartcollection", "Use constant SMART_COLLECTION_SLING_RES_TYPE from interface com.day.cq.dam.api.DamConstants")
				.put("dam/content/schemaeditors/forms", "Use constant SCHEMA_EDITOR_FORMS_BASE_DIR from interface com.day.cq.dam.api.DamConstants")
				.put("processingProfile", "Use constant PROCESSING_PROFILE from interface com.day.cq.dam.api.DamConstants")
				.put("metadataProfile", "Use constant METADATA_PROFILE from interface com.day.cq.dam.api.DamConstants")
				.put("videoProfile", "Use constant VIDEO_PROFILE from interface com.day.cq.dam.api.DamConstants")
				.put("imageProfile", "Use constant IMAGE_PROFILE from interface com.day.cq.dam.api.DamConstants")
				.put("folderThumbnail", "Use constant THUMBNAIL_NODE from interface com.day.cq.dam.api.DamConstants")
				.put("downloadUrl", "Use constant DOWNLOAD_URL from interface com.day.cq.dam.api.DamConstants")

				// interface com.day.cq.wcm.api.NameConstants
				.put("cq:Page", "Use constant NT_PAGE from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:PseudoPage", "Use constant NT_PSEUDO_PAGE from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:Template", "Use constant NT_TEMPLATE from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:Component", "Use constant NT_COMPONENT from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:EditConfig", "Use constant NT_EDIT_CONFIG from interface com.day.cq.wcm.api.NameConstants")
				.put("dialog", "Use constant NN_DIALOG from interface com.day.cq.wcm.api.NameConstants")
				.put("dialogPath", "Use constant PN_DIALOG_PATH from interface com.day.cq.wcm.api.NameConstants")
				.put("design_dialog", "Use constant NN_DESIGN_DIALOG from interface com.day.cq.wcm.api.NameConstants")
				.put("designDialogPath", "Use constant PN_DESIGN_DIALOG_PATH from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:editConfig", "Use constant NN_EDIT_CONFIG from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:childEditConfig", "Use constant NN_CHILD_EDIT_CONFIG from interface com.day.cq.wcm.api.NameConstants")
				.put("icon.png", "Use constant NN_ICON_PNG from interface com.day.cq.wcm.api.NameConstants")
				.put("thumbnail.png", "Use constant NN_THUMBNAIL_PNG from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:cellName", "Use constant PN_CELL_NAME from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:isContainer", "Use constant PN_IS_CONTAINER from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:noDecoration", "Use constant PN_NO_DECORATION from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:htmlTag", "Use constant NN_HTML_TAG from interface com.day.cq.wcm.api.NameConstants")
				.put("allowedPaths", "Use constant PN_ALLOWED_PATHS from interface com.day.cq.wcm.api.NameConstants")
				.put("allowedChildren", "Use constant PN_ALLOWED_CHILDREN from interface com.day.cq.wcm.api.NameConstants")
				.put("allowedParents", "Use constant PN_ALLOWED_PARENTS from interface com.day.cq.wcm.api.NameConstants")
				.put("componentGroup", "Use constant PN_COMPONENT_GROUP from interface com.day.cq.wcm.api.NameConstants")
				.put("sitePath", "Use constant PN_SITE_PATH from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:templatePath", "Use constant PN_TEMPLATE_PATH from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:template", "Use constant NN_TEMPLATE from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:tagName", "Use constant PN_TAG_NAME from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:infoProviders", "Use constant NN_INFO_PROVIDERS from interface com.day.cq.wcm.api.NameConstants")
				.put("className", "Use constant PN_CLASS_NAME from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:disableTargeting", "Use constant PN_DISABLE_TARGETING from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:layout", "Use constant PN_LAYOUT from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:dialogMode", "Use constant PN_DIALOG_MODE from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:inplaceEditing", "Use constant NN_INPLACE_EDITING from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:emptyText", "Use constant PN_EMPTY_TEXT from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:actions", "Use constant PN_ACTIONS from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:actionConfigs", "Use constant NN_ACTION_CONFIGS from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:formParameters", "Use constant NN_FORM_PARAMETERS from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:dropTargets", "Use constant NN_DROP_TARGETS from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:listeners", "Use constant NN_LISTENERS from interface com.day.cq.wcm.api.NameConstants")
				.put("propertyName", "Use constant PN_DT_NAME from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:inherit", "Use constant PN_INHERIT from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:designPath", "Use constant PN_DESIGN_PATH from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:parentPath", "Use constant PN_PARENT_PATH from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:childrenOrder", "Use constant PN_CHILDREN_ORDER from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:siblingOrder", "Use constant PN_SIBLING_ORDER from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:versionComment", "Use constant PN_VERSION_COMMENT from interface com.day.cq.wcm.api.NameConstants")
				.put("onTime", "Use constant PN_ON_TIME from interface com.day.cq.wcm.api.NameConstants")
				.put("offTime", "Use constant PN_OFF_TIME from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:lastModified", "Use constant PN_PAGE_LAST_MOD from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:lastModifiedBy", "Use constant PN_PAGE_LAST_MOD_BY from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:defaultView", "Use constant PN_DEFAULT_VIEW from interface com.day.cq.wcm.api.NameConstants")
				.put("sling:vanityPath", "Use constant PN_SLING_VANITY_PATH from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:allowedTemplates", "Use constant PN_ALLOWED_TEMPLATES from interface com.day.cq.wcm.api.NameConstants")

				// interface com.day.cq.wcm.webservicesupport.ConfigurationConstants
				.put("cq/cloudserviceconfigs/components/servicepage", "Use constant RT_SERVICE from interface com.day.cq.wcm.webservicesupport.ConfigurationConstants")
				.put("cq/cloudserviceconfigs/components/configpage", "Use constant RT_CONFIGURATION from interface com.day.cq.wcm.webservicesupport.ConfigurationConstants")
				.put("cq:cloudserviceconfigs", "Use constant PN_CONFIGURATIONS from interface com.day.cq.wcm.webservicesupport.ConfigurationConstants")

				// class org.apache.sling.jcr.resource.JcrResourceConstants
				.put("http://sling.apache.org/jcr/sling/1.0", "Use constant SLING_NAMESPACE_URI from class org.apache.sling.jcr.resource.JcrResourceConstants")
				.put("sling:resourceType", "Use constant SLING_RESOURCE_TYPE_PROPERTY from class org.apache.sling.jcr.resource.JcrResourceConstants")
				.put("sling:resourceSuperType", "Use constant SLING_RESOURCE_SUPER_TYPE_PROPERTY from class org.apache.sling.jcr.resource.JcrResourceConstants")
				.put("user.jcr.credentials", "Use constant AUTHENTICATION_INFO_CREDENTIALS from class org.apache.sling.jcr.resource.JcrResourceConstants")
				.put("user.jcr.workspace", "Use constant AUTHENTICATION_INFO_WORKSPACE from class org.apache.sling.jcr.resource.JcrResourceConstants")
				.put("user.jcr.session", "Use constant AUTHENTICATION_INFO_SESSION from class org.apache.sling.jcr.resource.JcrResourceConstants")
				.put("sling:Folder", "Use constant NT_SLING_FOLDER from class org.apache.sling.jcr.resource.JcrResourceConstants")
				.put("sling:OrderedFolder", "Use constant NT_SLING_ORDERED_FOLDER from class org.apache.sling.jcr.resource.JcrResourceConstants")

				// interface com.day.cq.tagging.TagConstants
				.put("cq:Tag", "Use constant NT_TAG from interface com.day.cq.tagging.TagConstants")
				.put("cq:Taggable", "Use constant NT_TAGGABLE from interface com.day.cq.tagging.TagConstants")
				.put("cq:movedTo", "Use constant PN_MOVED_TO from interface com.day.cq.tagging.TagConstants")
				.put("cq:backlinks", "Use constant PN_BACKLINKS from interface com.day.cq.tagging.TagConstants")

				// interface com.day.cq.replication.ReplicationStatus
				.put("cq:ReplicationStatus", "Use constant NODE_TYPE from interface com.day.cq.replication.ReplicationStatus")

				// interface org.apache.jackrabbit.vault.packaging.JcrPackage
				.put("application/zip", "Use constant MIME_TYPE from interface org.apache.jackrabbit.vault.packaging.JcrPackage")
				.put("vlt:definition", "Use constant NN_VLT_DEFINITION from interface org.apache.jackrabbit.vault.packaging.JcrPackage")
				.put("vlt:Package", "Use constant NT_VLT_PACKAGE from interface org.apache.jackrabbit.vault.packaging.JcrPackage")
				.put("vlt:PackageDefinition", "Use constant NT_VLT_PACKAGE_DEFINITION from interface org.apache.jackrabbit.vault.packaging.JcrPackage")

				// interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition
				.put("acHandling", "Use constant PN_AC_HANDLING from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("buildCount", "Use constant PN_BUILD_COUNT from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("cndPattern", "Use constant PN_CND_PATTERN from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("dependencies", "Use constant PN_DEPENDENCIES from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("noIntermediateSaves", "Use constant PN_DISABLE_INTERMEDIATE_SAVE from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("lastUnpacked", "Use constant PN_LAST_UNPACKED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("lastUnpackedBy", "Use constant PN_LAST_UNPACKED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("lastUnwrapped", "Use constant PN_LAST_UNWRAPPED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("lastUnwrappedBy", "Use constant PN_LAST_UNWRAPPED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("lastWrapped", "Use constant PN_LAST_WRAPPED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("lastWrappedBy", "Use constant PN_LAST_WRAPPED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("requiresRestart", "Use constant PN_REQUIRES_RESTART from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("requiresRoot", "Use constant PN_REQUIRES_ROOT from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("subPackages", "Use constant PN_SUB_PACKAGES from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")

				// interface org.apache.sling.api.resource.ResourceResolverFactory
				.put("user.password", "Use constant PASSWORD from interface org.apache.sling.api.resource.ResourceResolverFactory")
				.put("sling.service.subservice", "Use constant SUBSERVICE from interface org.apache.sling.api.resource.ResourceResolverFactory")
				.put("user.name", "Use constant USER from interface org.apache.sling.api.resource.ResourceResolverFactory")
				.put("user.impersonation", "Use constant USER_IMPERSONATION from interface org.apache.sling.api.resource.ResourceResolverFactory")

				// class com.adobe.granite.workflow.event.WorkflowEvent
				.put("Delagatee", "Use constant DELEGATEE from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("EventType", "Use constant EVENT_TYPE from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("fromNodeName", "Use constant FROM_NODE_NAME from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("JobFailed", "Use constant JOB_FAILED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("ModelDeleted", "Use constant MODEL_DELETED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("ModelDeployed", "Use constant MODEL_DEPLOYED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("NodeTransition", "Use constant NODE_TRANSITION_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("ParentWorkflowId", "Use constant PARENT_WORKFLOW_ID from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("ProcessTimeout", "Use constant PROCESS_TIMEOUT_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("ResourceCollectionModified", "Use constant RESOURCE_COLLECTION_MODIFIED from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("TimeStamp", "Use constant TIME_STAMP from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("toNodeName", "Use constant TO_NODE_NAME from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("User", "Use constant USER from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("VariableName", "Use constant VARIABLE_NAME from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("VariableUpdate", "Use constant VARIABLE_UPDATE_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("VariableValue", "Use constant VARIABLE_VALUE from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("Workdata", "Use constant WORK_DATA from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("Workitem", "Use constant WORK_ITEM from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("WorkflowAborted", "Use constant WORKFLOW_ABORTED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("WorkflowCompleted", "Use constant WORKFLOW_COMPLETED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("WorkflowInstanceId", "Use constant WORKFLOW_INSTANCE_ID from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("WorkflowName", "Use constant WORKFLOW_NAME from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("WorkflowNode", "Use constant WORKFLOW_NODE from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("WorkflowResumed", "Use constant WORKFLOW_RESUMED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("WorkflowStarted", "Use constant WORKFLOW_STARTED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("WorkflowSuspended", "Use constant WORKFLOW_SUSPENDED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("WorkflowVersion", "Use constant WORKFLOW_VERSION from class com.adobe.granite.workflow.event.WorkflowEvent")
				.put("WorkItemDelegated", "Use constant WORKITEM_DELEGATION_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent")

				// mix
				.put("cq:lastReplicated",
					"Use constant NODE_PROPERTY_LAST_REPLICATED from interface com.day.cq.replication.ReplicationStatus"
						+ " or constant PN_PAGE_LAST_REPLICATED from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:lastReplicatedBy",
					"Use constant NODE_PROPERTY_LAST_REPLICATED_BY from interface com.day.cq.replication.ReplicationStatus"
						+ " or constant PN_PAGE_LAST_REPLICATED_BY from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:lastReplicationAction",
					"Use constant NODE_PROPERTY_LAST_REPLICATION_ACTION from interface com.day.cq.replication.ReplicationStatus"
						+ " or constant PN_PAGE_LAST_REPLICATION_ACTION from interface com.day.cq.wcm.api.NameConstants")
				.put("cq:tags", "Use constant PN_TAGS from interface com.day.cq.tagging.TagConstants.PN_TAGS or interface com.day.cq.wcm.api.NameConstants")
				.put("cq:name", "Use constant PN_NAME from interface com.day.cq.dam.api.DamConstants.PN_NAME or interface com.day.cq.wcm.api.NameConstants")
				.put("jcr:created",
					"Use constant JCR_CREATED from interface com.day.cq.commons.jcr.JcrConstants"
						+ " or constant PN_CREATED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("jcr:createdBy",
					"Use constant JCR_CREATED_BY from interface com.day.cq.commons.jcr.JcrConstants"
						+ " or constant PN_CREATED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("jcr:description",
					"Use constant JCR_DESCRIPTION from interface com.day.cq.commons.jcr.JcrConstants"
						+ " or constant PN_DESCRIPTION from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("jcr:lastModified",
					"Use constant JCR_LASTMODIFIED from interface com.day.cq.commons.jcr.JcrConstants"
						+ " or constant PN_LASTMODIFIED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.put("jcr:lastModifiedBy", "Use constant JCR_LAST_MODIFIED_BY from interface com.day.cq.commons.jcr.JcrConstants"
					+ " or constant PN_LASTMODIFIED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
				.build();

		ANNOTATION_CONSTANTS = ImmutableMap.<String, String>builder()

				// class org.apache.sling.api.SlingConstants
				.put("org.apache.sling.api.include.servlet", "Use constant ATTR_REQUEST_SERVLET from class org.apache.sling.api.SlingConstants")
				.put("org.apache.sling.api.include.resource", "Use constant ATTR_REQUEST_CONTENT from class org.apache.sling.api.SlingConstants")
				.put("org.apache.sling.api.include.request_path_info", "Use constant ATTR_REQUEST_PATH_INFO from class org.apache.sling.api.SlingConstants")
				.put("javax.servlet.include.request_uri", "Use constant ATTR_INCLUDE_REQUEST_URI from class org.apache.sling.api.SlingConstants")
				.put("javax.servlet.include.context_path", "Use constant ATTR_INCLUDE_CONTEXT_PATH from class org.apache.sling.api.SlingConstants")
				.put("javax.servlet.include.servlet_path", "Use constant ATTR_INCLUDE_SERVLET_PATH from class org.apache.sling.api.SlingConstants")
				.put("javax.servlet.include.path_info", "Use constant ATTR_INCLUDE_PATH_INFO from class org.apache.sling.api.SlingConstants")
				.put("javax.servlet.include.query_string", "Use constant ATTR_INCLUDE_QUERY_STRING from class org.apache.sling.api.SlingConstants")
				.put("javax.servlet.error.exception", "Use constant ERROR_EXCEPTION from class org.apache.sling.api.SlingConstants")
				.put("javax.servlet.error.exception_type", "Use constant ERROR_EXCEPTION_TYPE from class org.apache.sling.api.SlingConstants")
				.put("javax.servlet.error.message", "Use constant ERROR_MESSAGE from class org.apache.sling.api.SlingConstants")
				.put("javax.servlet.error.request_uri", "Use constant ERROR_REQUEST_URI from class org.apache.sling.api.SlingConstants")
				.put("javax.servlet.error.servlet_name", "Use constant ERROR_SERVLET_NAME from class org.apache.sling.api.SlingConstants")
				.put("javax.servlet.error.status_code", "Use constant ERROR_STATUS from class org.apache.sling.api.SlingConstants")
				.put("org/apache/sling/api/resource/Resource/ADDED", "Use constant TOPIC_RESOURCE_ADDED from class org.apache.sling.api.SlingConstants")
				.put("org/apache/sling/api/resource/Resource/REMOVED", "Use constant TOPIC_RESOURCE_REMOVED from class org.apache.sling.api.SlingConstants")
				.put("org/apache/sling/api/resource/Resource/CHANGED", "Use constant TOPIC_RESOURCE_CHANGED from class org.apache.sling.api.SlingConstants")
				.put("org/apache/sling/api/resource/ResourceProvider/ADDED", "Use constant TOPIC_RESOURCE_PROVIDER_ADDED from class org.apache.sling.api.SlingConstants")
				.put("org/apache/sling/api/resource/ResourceProvider/REMOVED", "Use constant TOPIC_RESOURCE_PROVIDER_REMOVED from class org.apache.sling.api.SlingConstants")
				.put("org/apache/sling/api/resource/ResourceResolverMapping/CHANGED", "Use constant TOPIC_RESOURCE_RESOLVER_MAPPING_CHANGED from class org.apache.sling.api.SlingConstants")
				.put("path", "Use constant PROPERTY_PATH from class org.apache.sling.api.SlingConstants")
				.put("userid", "Use constant PROPERTY_USERID from class org.apache.sling.api.SlingConstants")
				.put("resourceType", "Use constant PROPERTY_RESOURCE_TYPE from class org.apache.sling.api.SlingConstants")
				.put("resourceSuperType", "Use constant PROPERTY_RESOURCE_SUPER_TYPE from class org.apache.sling.api.SlingConstants")
				.put("resourceChangedAttributes", "Use constant PROPERTY_CHANGED_ATTRIBUTES from class org.apache.sling.api.SlingConstants")
				.put("resourceAddedAttributes", "Use constant PROPERTY_ADDED_ATTRIBUTES from class org.apache.sling.api.SlingConstants")
				.put("resourceRemovedAttributes", "Use constant PROPERTY_REMOVED_ATTRIBUTES from class org.apache.sling.api.SlingConstants")
				.put("org/apache/sling/api/adapter/AdapterFactory/ADDED", "Use constant TOPIC_ADAPTER_FACTORY_ADDED from class org.apache.sling.api.SlingConstants")
				.put("org/apache/sling/api/adapter/AdapterFactory/REMOVED", "Use constant TOPIC_ADAPTER_FACTORY_REMOVED from class org.apache.sling.api.SlingConstants")
				.put("adaptables", "Use constant PROPERTY_ADAPTABLE_CLASSES from class org.apache.sling.api.SlingConstants")
				.put("adapters", "Use constant PROPERTY_ADAPTER_CLASSES from class org.apache.sling.api.SlingConstants")
				.put("sling.core.current.servletName", "Use constant SLING_CURRENT_SERVLET_NAME from class org.apache.sling.api.SlingConstants")

				// class org.apache.sling.api.servlets.HttpConstants
				.put("OPTIONS", "Use constant METHOD_OPTIONS from class org.apache.sling.api.servlets.HttpConstants")
				.put("GET", "Use constant METHOD_GET from class org.apache.sling.api.servlets.HttpConstants")
				.put("HEAD", "Use constant METHOD_HEAD from class org.apache.sling.api.servlets.HttpConstants")
				.put("POST", "Use constant METHOD_POST from class org.apache.sling.api.servlets.HttpConstants")
				.put("PUT", "Use constant METHOD_PUT from class org.apache.sling.api.servlets.HttpConstants")
				.put("DELETE", "Use constant METHOD_DELETE from class org.apache.sling.api.servlets.HttpConstants")
				.put("TRACE", "Use constant METHOD_TRACE from class org.apache.sling.api.servlets.HttpConstants")
				.put("CONNECT", "Use constant METHOD_CONNECT from class org.apache.sling.api.servlets.HttpConstants")
				.put("Accept", "Use constant HEADER_ACCEPT from class org.apache.sling.api.servlets.HttpConstants")
				.put("ETag", "Use constant HEADER_ETAG from class org.apache.sling.api.servlets.HttpConstants")
				.put("If-Match", "Use constant HEADER_IF_MATCH from class org.apache.sling.api.servlets.HttpConstants")
				.put("If-Modified-Since", "Use constant HEADER_IF_MODIFIED_SINCE from class org.apache.sling.api.servlets.HttpConstants")
				.put("Last-Modified", "Use constant HEADER_LAST_MODIFIED from class org.apache.sling.api.servlets.HttpConstants")

				// interface org.osgi.framework.Constants
				.put("System Bundle", "Use constant SYSTEM_BUNDLE_LOCATION from interface org.osgi.framework.Constants")
				.put("system.bundle", "Use constant SYSTEM_BUNDLE_SYMBOLICNAME from interface org.osgi.framework.Constants")
				.put("Bundle-Category", "Use constant BUNDLE_CATEGORY from interface org.osgi.framework.Constants")
				.put("Bundle-ClassPath", "Use constant BUNDLE_CLASSPATH from interface org.osgi.framework.Constants")
				.put("Bundle-Copyright", "Use constant BUNDLE_COPYRIGHT from interface org.osgi.framework.Constants")
				.put("Bundle-Description", "Use constant BUNDLE_DESCRIPTION from interface org.osgi.framework.Constants")
				.put("Bundle-Name", "Use constant BUNDLE_NAME from interface org.osgi.framework.Constants")
				.put("Bundle-NativeCode", "Use constant BUNDLE_NATIVECODE from interface org.osgi.framework.Constants")
				.put("Export-Package", "Use constant EXPORT_PACKAGE from interface org.osgi.framework.Constants")
				.put("Export-Service", "Use constant EXPORT_SERVICE from interface org.osgi.framework.Constants")
				.put("Import-Package", "Use constant IMPORT_PACKAGE from interface org.osgi.framework.Constants")
				.put("DynamicImport-Package", "Use constant DYNAMICIMPORT_PACKAGE from interface org.osgi.framework.Constants")
				.put("Import-Service", "Use constant IMPORT_SERVICE from interface org.osgi.framework.Constants")
				.put("Bundle-Vendor", "Use constant BUNDLE_VENDOR from interface org.osgi.framework.Constants")
				.put("Bundle-Version", "Use constant BUNDLE_VERSION from interface org.osgi.framework.Constants")
				.put("Bundle-DocURL", "Use constant BUNDLE_DOCURL from interface org.osgi.framework.Constants")
				.put("Bundle-ContactAddress", "Use constant BUNDLE_CONTACTADDRESS from interface org.osgi.framework.Constants")
				.put("Bundle-Activator", "Use constant BUNDLE_ACTIVATOR from interface org.osgi.framework.Constants")
				.put("Bundle-UpdateLocation", "Use constant BUNDLE_UPDATELOCATION from interface org.osgi.framework.Constants")
				.put("specification-version", "Use constant PACKAGE_SPECIFICATION_VERSION from interface org.osgi.framework.Constants")
				.put("processor", "Use constant BUNDLE_NATIVECODE_PROCESSOR from interface org.osgi.framework.Constants")
				.put("osname", "Use constant BUNDLE_NATIVECODE_OSNAME from interface org.osgi.framework.Constants")
				.put("osversion", "Use constant BUNDLE_NATIVECODE_OSVERSION from interface org.osgi.framework.Constants")
				.put("language", "Use constant BUNDLE_NATIVECODE_LANGUAGE from interface org.osgi.framework.Constants")
				.put("Bundle-RequiredExecutionEnvironment", "Use constant BUNDLE_REQUIREDEXECUTIONENVIRONMENT from interface org.osgi.framework.Constants")
				.put("Bundle-SymbolicName", "Use constant BUNDLE_SYMBOLICNAME from interface org.osgi.framework.Constants")
				.put("singleton", "Use constant SINGLETON_DIRECTIVE from interface org.osgi.framework.Constants")
				.put("fragment-attachment", "Use constant FRAGMENT_ATTACHMENT_DIRECTIVE from interface org.osgi.framework.Constants")
				.put("always", "Use constant FRAGMENT_ATTACHMENT_ALWAYS from interface org.osgi.framework.Constants")
				.put("resolve-time", "Use constant FRAGMENT_ATTACHMENT_RESOLVETIME from interface org.osgi.framework.Constants")
				.put("never", "Use constant FRAGMENT_ATTACHMENT_NEVER from interface org.osgi.framework.Constants")
				.put("Bundle-Localization", "Use constant BUNDLE_LOCALIZATION from interface org.osgi.framework.Constants")
				.put("OSGI-INF/l10n/bundle", "Use constant BUNDLE_LOCALIZATION_DEFAULT_BASENAME from interface org.osgi.framework.Constants")
				.put("Require-Bundle", "Use constant REQUIRE_BUNDLE from interface org.osgi.framework.Constants")
				.put("bundle-version", "Use constant BUNDLE_VERSION_ATTRIBUTE from interface org.osgi.framework.Constants")
				.put("Fragment-Host", "Use constant FRAGMENT_HOST from interface org.osgi.framework.Constants")
				.put("selection-filter", "Use constant SELECTION_FILTER_ATTRIBUTE from interface org.osgi.framework.Constants")
				.put("Bundle-ManifestVersion", "Use constant BUNDLE_MANIFESTVERSION from interface org.osgi.framework.Constants")
				.put("version", "Use constant VERSION_ATTRIBUTE from interface org.osgi.framework.Constants")
				.put("bundle-symbolic-name", "Use constant BUNDLE_SYMBOLICNAME_ATTRIBUTE from interface org.osgi.framework.Constants")
				.put("resolution", "Use constant RESOLUTION_DIRECTIVE from interface org.osgi.framework.Constants")
				.put("optional", "Use constant RESOLUTION_OPTIONAL from interface org.osgi.framework.Constants")
				.put("uses", "Use constant USES_DIRECTIVE from interface org.osgi.framework.Constants")
				.put("include", "Use constant INCLUDE_DIRECTIVE from interface org.osgi.framework.Constants")
				.put("exclude", "Use constant EXCLUDE_DIRECTIVE from interface org.osgi.framework.Constants")
				.put("mandatory", "Use constant MANDATORY_DIRECTIVE from interface org.osgi.framework.Constants")
				.put("visibility", "Use constant VISIBILITY_DIRECTIVE from interface org.osgi.framework.Constants")
				.put("private", "Use constant VISIBILITY_PRIVATE from interface org.osgi.framework.Constants")
				.put("reexport", "Use constant VISIBILITY_REEXPORT from interface org.osgi.framework.Constants")
				.put("extension", "Use constant EXTENSION_DIRECTIVE from interface org.osgi.framework.Constants")
				.put("framework", "Use constant EXTENSION_FRAMEWORK from interface org.osgi.framework.Constants")
				.put("bootclasspath", "Use constant EXTENSION_BOOTCLASSPATH from interface org.osgi.framework.Constants")
				.put("Bundle-ActivationPolicy", "Use constant BUNDLE_ACTIVATIONPOLICY from interface org.osgi.framework.Constants")
				.put("lazy", "Use constant ACTIVATION_LAZY from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.version", "Use constant FRAMEWORK_VERSION from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.vendor", "Use constant FRAMEWORK_VENDOR from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.language", "Use constant FRAMEWORK_LANGUAGE from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.os.name", "Use constant FRAMEWORK_OS_NAME from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.os.version", "Use constant FRAMEWORK_OS_VERSION from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.processor", "Use constant FRAMEWORK_PROCESSOR from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.executionenvironment", "Use constant FRAMEWORK_EXECUTIONENVIRONMENT from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.bootdelegation", "Use constant FRAMEWORK_BOOTDELEGATION from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.system.packages", "Use constant FRAMEWORK_SYSTEMPACKAGES from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.system.packages.extra", "Use constant FRAMEWORK_SYSTEMPACKAGES_EXTRA from interface org.osgi.framework.Constants")
				.put("org.osgi.supports.framework.extension", "Use constant SUPPORTS_FRAMEWORK_EXTENSION from interface org.osgi.framework.Constants")
				.put("org.osgi.supports.bootclasspath.extension", "Use constant SUPPORTS_BOOTCLASSPATH_EXTENSION from interface org.osgi.framework.Constants")
				.put("org.osgi.supports.framework.fragment", "Use constant SUPPORTS_FRAMEWORK_FRAGMENT from interface org.osgi.framework.Constants")
				.put("org.osgi.supports.framework.requirebundle", "Use constant SUPPORTS_FRAMEWORK_REQUIREBUNDLE from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.security", "Use constant FRAMEWORK_SECURITY from interface org.osgi.framework.Constants")
				.put("osgi", "Use constant FRAMEWORK_SECURITY_OSGI from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.storage", "Use constant FRAMEWORK_STORAGE from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.storage.clean", "Use constant FRAMEWORK_STORAGE_CLEAN from interface org.osgi.framework.Constants")
				.put("onFirstInit", "Use constant FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.library.extensions", "Use constant FRAMEWORK_LIBRARY_EXTENSIONS from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.command.execpermission", "Use constant FRAMEWORK_EXECPERMISSION from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.trust.repositories", "Use constant FRAMEWORK_TRUST_REPOSITORIES from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.windowsystem", "Use constant FRAMEWORK_WINDOWSYSTEM from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.startlevel.beginning", "Use constant FRAMEWORK_BEGINNING_STARTLEVEL from interface org.osgi.framework.Constants")
				.put("org.osgi.framework.bundle.parent", "Use constant FRAMEWORK_BUNDLE_PARENT from interface org.osgi.framework.Constants")
				.put("service.id", "Use constant SERVICE_ID from interface org.osgi.framework.Constants")
				.put("service.pid", "Use constant SERVICE_PID from interface org.osgi.framework.Constants")
				.put("service.ranking", "Use constant SERVICE_RANKING from interface org.osgi.framework.Constants")
				.put("service.vendor", "Use constant SERVICE_VENDOR from interface org.osgi.framework.Constants")
				.put("service.description", "Use constant SERVICE_DESCRIPTION from interface org.osgi.framework.Constants")

				// class org.apache.sling.engine.EngineConstants
				.put("javax.servlet.Filter", "Use constant FILTER_NAME from class org.apache.sling.engine.EngineConstants")
				.put("sling.filter.scope", "Use constant SLING_FILTER_SCOPE from class org.apache.sling.engine.EngineConstants")
				.put("COMPONENT", "Use constant FILTER_SCOPE_COMPONENT from class org.apache.sling.engine.EngineConstants")
				.put("ERROR", "Use constant FILTER_SCOPE_ERROR from class org.apache.sling.engine.EngineConstants")
				.put("INCLUDE", "Use constant FILTER_SCOPE_INCLUDE from class org.apache.sling.engine.EngineConstants")
				.put("FORWARD", "Use constant FILTER_SCOPE_FORWARD from class org.apache.sling.engine.EngineConstants")
				.put("REQUEST", "Use constant FILTER_SCOPE_REQUEST from class org.apache.sling.engine.EngineConstants")

				// class com.adobe.granite.workflow.event.WorkflowEvent
				.put("com/adobe/granite/workflow/event", "Use constant EVENT_TOPIC from class com.adobe.granite.workflow.event.WorkflowEvent")

				.build();
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

	public static String getMessageForConstant(String value) {
		return CONSTANTS.get(value);
	}

	public static boolean isConstant(String value) {
		return CONSTANTS.containsKey(value);
	}

}
