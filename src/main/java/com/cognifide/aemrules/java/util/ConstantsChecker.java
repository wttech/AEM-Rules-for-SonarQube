/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2019 Cognifide Limited
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
package com.cognifide.aemrules.java.util;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstantsChecker {

    private static final Map<String, String> CONSTANTS = Stream.of(
            // interface com.day.cq.commons.jcr.JcrConstants
            new AbstractMap.SimpleImmutableEntry<>("jcr:autoCreated", "JCR_AUTOCREATED from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:baseVersion", "JCR_BASEVERSION from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:child", "JCR_CHILD from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:childNodeDefinition", "JCR_CHILDNODEDEFINITION from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:content", "JCR_CONTENT from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:data", "JCR_DATA from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:defaultPrimaryType", "JCR_DEFAULTPRIMARYTYPE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:defaultValues", "JCR_DEFAULTVALUES from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:encoding", "JCR_ENCODING from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:frozenMixinTypes", "JCR_FROZENMIXINTYPES from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:frozenNode", "JCR_FROZENNODE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:frozenPrimaryType", "JCR_FROZENPRIMARYTYPE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:frozenUuid", "JCR_FROZENUUID from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:hasOrderableChildNodes", "JCR_HASORDERABLECHILDNODES from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:isCheckedOut", "JCR_ISCHECKEDOUT from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:isMixin", "JCR_ISMIXIN from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:language", "JCR_LANGUAGE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:lockIsDeep", "JCR_LOCKISDEEP from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:lockOwner", "JCR_LOCKOWNER from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:mandatory", "JCR_MANDATORY from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:mergeFailed", "JCR_MERGEFAILED from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:mimeType", "JCR_MIMETYPE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:mixinTypes", "JCR_MIXINTYPES from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:multiple", "JCR_MULTIPLE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:name", "JCR_NAME from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:nodeTypeName", "JCR_NODETYPENAME from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:onParentVersion", "JCR_ONPARENTVERSION from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:predecessors", "JCR_PREDECESSORS from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:primaryItemName", "JCR_PRIMARYITEMNAME from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:primaryType", "JCR_PRIMARYTYPE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:propertyDefinition", "JCR_PROPERTYDEFINITION from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:protected", "JCR_PROTECTED from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:requiredPrimaryTypes", "JCR_REQUIREDPRIMARYTYPES from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:requiredType", "JCR_REQUIREDTYPE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:rootVersion", "JCR_ROOTVERSION from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:sameNameSiblings", "JCR_SAMENAMESIBLINGS from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:statement", "JCR_STATEMENT from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:successors", "JCR_SUCCESSORS from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:supertypes", "JCR_SUPERTYPES from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:system", "JCR_SYSTEM from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:uuid", "JCR_UUID from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:valueConstraints", "JCR_VALUECONSTRAINTS from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:versionHistory", "JCR_VERSIONHISTORY from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:versionLabels", "JCR_VERSIONLABELS from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:versionStorage", "JCR_VERSIONSTORAGE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:versionableUuid", "JCR_VERSIONABLEUUID from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:path", "JCR_PATH from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:score", "JCR_SCORE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("mix:lockable", "MIX_LOCKABLE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("mix:referenceable", "MIX_REFERENCEABLE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("mix:versionable", "MIX_VERSIONABLE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:base", "NT_BASE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:childNodeDefinition", "NT_CHILDNODEDEFINITION from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:file", "NT_FILE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:folder", "NT_FOLDER from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:frozenNode", "NT_FROZENNODE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:hierarchyNode", "NT_HIERARCHYNODE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:linkedFile", "NT_LINKEDFILE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:nodeType", "NT_NODETYPE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:propertyDefinition", "NT_PROPERTYDEFINITION from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:query", "NT_QUERY from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:resource", "NT_RESOURCE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:unstructured", "NT_UNSTRUCTURED from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:version", "NT_VERSION from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:versionHistory", "NT_VERSIONHISTORY from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:versionLabels", "NT_VERSIONLABELS from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("nt:versionedChild", "NT_VERSIONEDCHILD from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:title", "JCR_TITLE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("mix:title", "MIX_TITLE from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("mix:created", "MIX_CREATED from interface com.day.cq.commons.jcr.JcrConstants"),
            new AbstractMap.SimpleImmutableEntry<>("mix:lastModified", "MIX_LAST_MODIFIED from interface com.day.cq.commons.jcr.JcrConstants"),

            // interface com.day.cq.dam.api.DamConstants
            new AbstractMap.SimpleImmutableEntry<>("dam:Asset", "NT_DAM_ASSET from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dam:AssetContent", "NT_DAM_ASSETCONTENT from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dam:extracted", "PN_EXTRACTED from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dam:sha1", "PN_SHA1 from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dam:size", "DAM_SIZE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:versionCreator", "PN_VERSION_CREATOR from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:contributor", "DC_CONTRIBUTOR from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:coverage", "DC_COVERAGE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:creator", "DC_CREATOR from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:date", "DC_DATE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:description", "DC_DESCRIPTION from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:extent", "DC_EXTENT from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:format", "DC_FORMAT from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:identifier", "DC_IDENTIFIER from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:language", "DC_LANGUAGE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:modified", "DC_MODIFIED from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:publisher", "DC_PUBLISHER from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:relation", "DC_RELATION from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:rights", "DC_RIGHTS from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:subject", "DC_SUBJECT from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:title", "DC_TITLE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dc:type", "DC_TYPE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq5dam.thumbnail", "PREFIX_ASSET_THUMBNAIL from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("exif:PixelXDimension", "EXIF_PIXELXDIMENSION from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("exif:PixelYDimension", "EXIF_PIXELYDIMENSION from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("tiff:ImageLength", "TIFF_IMAGELENGTH from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("tiff:ImageWidth", "TIFF_IMAGEWIDTH from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("assetExpired", "ACTIVITY_TYPE_ASSET_EXPIRED from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("assetExpiring", "ACTIVITY_TYPE_ASSET_EXPIRING from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dam:lastPostExpirationRun", "LAST_EXPIRY_NOTIFICATION_PROPNAME from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dam/collection", "COLLECTION_SLING_RES_TYPE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dam/smartcollection", "SMART_COLLECTION_SLING_RES_TYPE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dam/content/schemaeditors/forms", "SCHEMA_EDITOR_FORMS_BASE_DIR from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("processingProfile", "PROCESSING_PROFILE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("metadataProfile", "METADATA_PROFILE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("videoProfile", "VIDEO_PROFILE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("imageProfile", "IMAGE_PROFILE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("folderThumbnail", "THUMBNAIL_NODE from interface com.day.cq.dam.api.DamConstants"),
            new AbstractMap.SimpleImmutableEntry<>("downloadUrl", "DOWNLOAD_URL from interface com.day.cq.dam.api.DamConstants"),

            // interface com.day.cq.wcm.api.NameConstants
            new AbstractMap.SimpleImmutableEntry<>("cq:Page", "NT_PAGE from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:PseudoPage", "NT_PSEUDO_PAGE from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:Template", "NT_TEMPLATE from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:Component", "NT_COMPONENT from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:EditConfig", "NT_EDIT_CONFIG from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dialog", "NN_DIALOG from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("dialogPath", "PN_DIALOG_PATH from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("design_dialog", "NN_DESIGN_DIALOG from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("designDialogPath", "PN_DESIGN_DIALOG_PATH from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:editConfig", "NN_EDIT_CONFIG from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:childEditConfig", "NN_CHILD_EDIT_CONFIG from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("icon.png", "NN_ICON_PNG from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("thumbnail.png", "NN_THUMBNAIL_PNG from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:cellName", "PN_CELL_NAME from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:isContainer", "PN_IS_CONTAINER from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:noDecoration", "PN_NO_DECORATION from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:htmlTag", "NN_HTML_TAG from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("allowedPaths", "PN_ALLOWED_PATHS from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("allowedChildren", "PN_ALLOWED_CHILDREN from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("allowedParents", "PN_ALLOWED_PARENTS from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("componentGroup", "PN_COMPONENT_GROUP from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("sitePath", "PN_SITE_PATH from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:templatePath", "PN_TEMPLATE_PATH from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:template", "NN_TEMPLATE from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:tagName", "PN_TAG_NAME from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:infoProviders", "NN_INFO_PROVIDERS from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("className", "PN_CLASS_NAME from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:disableTargeting", "PN_DISABLE_TARGETING from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:layout", "PN_LAYOUT from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:dialogMode", "PN_DIALOG_MODE from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:inplaceEditing", "NN_INPLACE_EDITING from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:emptyText", "PN_EMPTY_TEXT from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:actions", "PN_ACTIONS from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:actionConfigs", "NN_ACTION_CONFIGS from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:formParameters", "NN_FORM_PARAMETERS from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:dropTargets", "NN_DROP_TARGETS from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:listeners", "NN_LISTENERS from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("propertyName", "PN_DT_NAME from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:inherit", "PN_INHERIT from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:designPath", "PN_DESIGN_PATH from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:parentPath", "PN_PARENT_PATH from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:childrenOrder", "PN_CHILDREN_ORDER from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:siblingOrder", "PN_SIBLING_ORDER from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:versionComment", "PN_VERSION_COMMENT from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("onTime", "PN_ON_TIME from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("offTime", "PN_OFF_TIME from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:lastModified", "PN_PAGE_LAST_MOD from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:lastModifiedBy", "PN_PAGE_LAST_MOD_BY from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:defaultView", "PN_DEFAULT_VIEW from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("sling:vanityPath", "PN_SLING_VANITY_PATH from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:allowedTemplates", "PN_ALLOWED_TEMPLATES from interface com.day.cq.wcm.api.NameConstants"),

            // interface com.day.cq.wcm.webservicesupport.ConfigurationConstants
            new AbstractMap.SimpleImmutableEntry<>("cq/cloudserviceconfigs/components/servicepage", "RT_SERVICE from interface com.day.cq.wcm.webservicesupport.ConfigurationConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq/cloudserviceconfigs/components/configpage", "RT_CONFIGURATION from interface com.day.cq.wcm.webservicesupport.ConfigurationConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:cloudserviceconfigs", "PN_CONFIGURATIONS from interface com.day.cq.wcm.webservicesupport.ConfigurationConstants"),

            // class org.apache.sling.jcr.resource.JcrResourceConstants
            new AbstractMap.SimpleImmutableEntry<>("http://sling.apache.org/jcr/sling/1.0", "SLING_NAMESPACE_URI from class org.apache.sling.jcr.resource.JcrResourceConstants"),
            new AbstractMap.SimpleImmutableEntry<>("sling:resourceType", "SLING_RESOURCE_TYPE_PROPERTY from class org.apache.sling.jcr.resource.JcrResourceConstants"),
            new AbstractMap.SimpleImmutableEntry<>("sling:resourceSuperType", "SLING_RESOURCE_SUPER_TYPE_PROPERTY from class org.apache.sling.jcr.resource.JcrResourceConstants"),
            new AbstractMap.SimpleImmutableEntry<>("user.jcr.credentials", "AUTHENTICATION_INFO_CREDENTIALS from class org.apache.sling.jcr.resource.JcrResourceConstants"),
            new AbstractMap.SimpleImmutableEntry<>("user.jcr.workspace", "AUTHENTICATION_INFO_WORKSPACE from class org.apache.sling.jcr.resource.JcrResourceConstants"),
            new AbstractMap.SimpleImmutableEntry<>("user.jcr.session", "AUTHENTICATION_INFO_SESSION from class org.apache.sling.jcr.resource.JcrResourceConstants"),
            new AbstractMap.SimpleImmutableEntry<>("sling:Folder", "NT_SLING_FOLDER from class org.apache.sling.jcr.resource.JcrResourceConstants"),
            new AbstractMap.SimpleImmutableEntry<>("sling:OrderedFolder", "NT_SLING_ORDERED_FOLDER from class org.apache.sling.jcr.resource.JcrResourceConstants"),

            // interface com.day.cq.tagging.TagConstants
            new AbstractMap.SimpleImmutableEntry<>("cq:Tag", "NT_TAG from interface com.day.cq.tagging.TagConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:Taggable", "NT_TAGGABLE from interface com.day.cq.tagging.TagConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:movedTo", "PN_MOVED_TO from interface com.day.cq.tagging.TagConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:backlinks", "PN_BACKLINKS from interface com.day.cq.tagging.TagConstants"),

            // interface com.day.cq.replication.ReplicationStatus
            new AbstractMap.SimpleImmutableEntry<>("cq:ReplicationStatus", "NODE_TYPE from interface com.day.cq.replication.ReplicationStatus"),

            // interface org.apache.jackrabbit.vault.packaging.JcrPackage
            new AbstractMap.SimpleImmutableEntry<>("application/zip", "MIME_TYPE from interface org.apache.jackrabbit.vault.packaging.JcrPackage"),
            new AbstractMap.SimpleImmutableEntry<>("vlt:definition", "NN_VLT_DEFINITION from interface org.apache.jackrabbit.vault.packaging.JcrPackage"),
            new AbstractMap.SimpleImmutableEntry<>("vlt:Package", "NT_VLT_PACKAGE from interface org.apache.jackrabbit.vault.packaging.JcrPackage"),
            new AbstractMap.SimpleImmutableEntry<>("vlt:PackageDefinition", "NT_VLT_PACKAGE_DEFINITION from interface org.apache.jackrabbit.vault.packaging.JcrPackage"),

            // interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition
            new AbstractMap.SimpleImmutableEntry<>("acHandling", "PN_AC_HANDLING from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("buildCount", "PN_BUILD_COUNT from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("cndPattern", "PN_CND_PATTERN from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("dependencies", "PN_DEPENDENCIES from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("noIntermediateSaves", "PN_DISABLE_INTERMEDIATE_SAVE from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("lastUnpacked", "PN_LAST_UNPACKED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("lastUnpackedBy", "PN_LAST_UNPACKED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("lastUnwrapped", "PN_LAST_UNWRAPPED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("lastUnwrappedBy", "PN_LAST_UNWRAPPED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("lastWrapped", "PN_LAST_WRAPPED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("lastWrappedBy", "PN_LAST_WRAPPED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("requiresRestart", "PN_REQUIRES_RESTART from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("requiresRoot", "PN_REQUIRES_ROOT from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("subPackages", "PN_SUB_PACKAGES from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),

            // interface org.apache.sling.api.resource.ResourceResolverFactory
            new AbstractMap.SimpleImmutableEntry<>("user.password", "PASSWORD from interface org.apache.sling.api.resource.ResourceResolverFactory"),
            new AbstractMap.SimpleImmutableEntry<>("sling.service.subservice", "SUBSERVICE from interface org.apache.sling.api.resource.ResourceResolverFactory"),
            new AbstractMap.SimpleImmutableEntry<>("user.name", "USER from interface org.apache.sling.api.resource.ResourceResolverFactory"),
            new AbstractMap.SimpleImmutableEntry<>("user.impersonation", "USER_IMPERSONATION from interface org.apache.sling.api.resource.ResourceResolverFactory"),

            // class com.adobe.granite.workflow.event.WorkflowEvent
            new AbstractMap.SimpleImmutableEntry<>("Delagatee", "DELEGATEE from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("EventType", "EVENT_TYPE from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("fromNodeName", "FROM_NODE_NAME from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("JobFailed", "JOB_FAILED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("ModelDeleted", "MODEL_DELETED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("ModelDeployed", "MODEL_DEPLOYED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("NodeTransition", "NODE_TRANSITION_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("ParentWorkflowId", "PARENT_WORKFLOW_ID from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("ProcessTimeout", "PROCESS_TIMEOUT_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("ResourceCollectionModified", "RESOURCE_COLLECTION_MODIFIED from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("TimeStamp", "TIME_STAMP from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("toNodeName", "TO_NODE_NAME from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("User", "USER from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("VariableName", "VARIABLE_NAME from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("VariableUpdate", "VARIABLE_UPDATE_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("VariableValue", "VARIABLE_VALUE from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("Workdata", "WORK_DATA from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("Workitem", "WORK_ITEM from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("WorkflowAborted", "WORKFLOW_ABORTED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("WorkflowCompleted", "WORKFLOW_COMPLETED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("WorkflowInstanceId", "WORKFLOW_INSTANCE_ID from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("WorkflowName", "WORKFLOW_NAME from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("WorkflowNode", "WORKFLOW_NODE from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("WorkflowResumed", "WORKFLOW_RESUMED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("WorkflowStarted", "WORKFLOW_STARTED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("WorkflowSuspended", "WORKFLOW_SUSPENDED_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("WorkflowVersion", "WORKFLOW_VERSION from class com.adobe.granite.workflow.event.WorkflowEvent"),
            new AbstractMap.SimpleImmutableEntry<>("WorkItemDelegated", "WORKITEM_DELEGATION_EVENT from class com.adobe.granite.workflow.event.WorkflowEvent"),

            // mix
            new AbstractMap.SimpleImmutableEntry<>("cq:lastReplicated",
                    "NODE_PROPERTY_LAST_REPLICATED from interface com.day.cq.replication.ReplicationStatus"
                            + " or constant PN_PAGE_LAST_REPLICATED from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:lastReplicatedBy",
                    "NODE_PROPERTY_LAST_REPLICATED_BY from interface com.day.cq.replication.ReplicationStatus"
                            + " or constant PN_PAGE_LAST_REPLICATED_BY from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:lastReplicationAction",
                    "NODE_PROPERTY_LAST_REPLICATION_ACTION from interface com.day.cq.replication.ReplicationStatus"
                            + " or constant PN_PAGE_LAST_REPLICATION_ACTION from interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:tags", "PN_TAGS from interface com.day.cq.tagging.TagConstants.PN_TAGS or interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("cq:name", "PN_NAME from interface com.day.cq.dam.api.DamConstants.PN_NAME or interface com.day.cq.wcm.api.NameConstants"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:created",
                    "JCR_CREATED from interface com.day.cq.commons.jcr.JcrConstants"
                            + " or constant PN_CREATED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:createdBy",
                    "JCR_CREATED_BY from interface com.day.cq.commons.jcr.JcrConstants"
                            + " or constant PN_CREATED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:description",
                    "JCR_DESCRIPTION from interface com.day.cq.commons.jcr.JcrConstants"
                            + " or constant PN_DESCRIPTION from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:lastModified",
                    "JCR_LASTMODIFIED from interface com.day.cq.commons.jcr.JcrConstants"
                            + " or constant PN_LASTMODIFIED from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition"),
            new AbstractMap.SimpleImmutableEntry<>("jcr:lastModifiedBy", "JCR_LAST_MODIFIED_BY from interface com.day.cq.commons.jcr.JcrConstants"
                    + " or constant PN_LASTMODIFIED_BY from interface org.apache.jackrabbit.vault.packaging.JcrPackageDefinition")
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private static final Map<String, String> ANNOTATION_CONSTANTS = Stream.of(
            // class org.apache.sling.api.SlingConstants
            new AbstractMap.SimpleImmutableEntry<>("org.apache.sling.api.include.servlet", "ATTR_REQUEST_SERVLET from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("org.apache.sling.api.include.resource", "ATTR_REQUEST_CONTENT from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("org.apache.sling.api.include.request_path_info", "ATTR_REQUEST_PATH_INFO from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.include.request_uri", "ATTR_INCLUDE_REQUEST_URI from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.include.context_path", "ATTR_INCLUDE_CONTEXT_PATH from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.include.servlet_path", "ATTR_INCLUDE_SERVLET_PATH from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.include.path_info", "ATTR_INCLUDE_PATH_INFO from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.include.query_string", "ATTR_INCLUDE_QUERY_STRING from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.error.exception", "ERROR_EXCEPTION from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.error.exception_type", "ERROR_EXCEPTION_TYPE from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.error.message", "ERROR_MESSAGE from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.error.request_uri", "ERROR_REQUEST_URI from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.error.servlet_name", "ERROR_SERVLET_NAME from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.error.status_code", "ERROR_STATUS from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("org/apache/sling/api/resource/Resource/ADDED", "TOPIC_RESOURCE_ADDED from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("org/apache/sling/api/resource/Resource/REMOVED", "TOPIC_RESOURCE_REMOVED from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("org/apache/sling/api/resource/Resource/CHANGED", "TOPIC_RESOURCE_CHANGED from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("org/apache/sling/api/resource/ResourceProvider/ADDED", "TOPIC_RESOURCE_PROVIDER_ADDED from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("org/apache/sling/api/resource/ResourceProvider/REMOVED", "TOPIC_RESOURCE_PROVIDER_REMOVED from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("org/apache/sling/api/resource/ResourceResolverMapping/CHANGED",
                    "TOPIC_RESOURCE_RESOLVER_MAPPING_CHANGED from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("path", "PROPERTY_PATH from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("userid", "PROPERTY_USERID from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("resourceType", "PROPERTY_RESOURCE_TYPE from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("resourceSuperType", "PROPERTY_RESOURCE_SUPER_TYPE from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("resourceChangedAttributes", "PROPERTY_CHANGED_ATTRIBUTES from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("resourceAddedAttributes", "PROPERTY_ADDED_ATTRIBUTES from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("resourceRemovedAttributes", "PROPERTY_REMOVED_ATTRIBUTES from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("org/apache/sling/api/adapter/AdapterFactory/ADDED", "TOPIC_ADAPTER_FACTORY_ADDED from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("org/apache/sling/api/adapter/AdapterFactory/REMOVED", "TOPIC_ADAPTER_FACTORY_REMOVED from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("adaptables", "PROPERTY_ADAPTABLE_CLASSES from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("adapters", "PROPERTY_ADAPTER_CLASSES from class org.apache.sling.api.SlingConstants"),
            new AbstractMap.SimpleImmutableEntry<>("sling.core.current.servletName", "SLING_CURRENT_SERVLET_NAME from class org.apache.sling.api.SlingConstants"),

            // class org.apache.sling.api.servlets.HttpConstants
            new AbstractMap.SimpleImmutableEntry<>("OPTIONS", "METHOD_OPTIONS from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("GET", "METHOD_GET from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("HEAD", "METHOD_HEAD from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("POST", "METHOD_POST from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("PUT", "METHOD_PUT from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("DELETE", "METHOD_DELETE from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("TRACE", "METHOD_TRACE from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("CONNECT", "METHOD_CONNECT from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("Accept", "HEADER_ACCEPT from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("ETag", "HEADER_ETAG from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("If-Match", "HEADER_IF_MATCH from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("If-Modified-Since", "HEADER_IF_MODIFIED_SINCE from class org.apache.sling.api.servlets.HttpConstants"),
            new AbstractMap.SimpleImmutableEntry<>("Last-Modified", "HEADER_LAST_MODIFIED from class org.apache.sling.api.servlets.HttpConstants"),

            // interface org.osgi.framework.Constants
            new AbstractMap.SimpleImmutableEntry<>("System Bundle", "SYSTEM_BUNDLE_LOCATION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("system.bundle", "SYSTEM_BUNDLE_SYMBOLICNAME from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-Category", "BUNDLE_CATEGORY from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-ClassPath", "BUNDLE_CLASSPATH from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-Copyright", "BUNDLE_COPYRIGHT from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-Description", "BUNDLE_DESCRIPTION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-Name", "BUNDLE_NAME from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-NativeCode", "BUNDLE_NATIVECODE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Export-Package", "EXPORT_PACKAGE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Export-Service", "EXPORT_SERVICE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Import-Package", "IMPORT_PACKAGE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("DynamicImport-Package", "DYNAMICIMPORT_PACKAGE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Import-Service", "IMPORT_SERVICE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-Vendor", "BUNDLE_VENDOR from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-Version", "BUNDLE_VERSION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-DocURL", "BUNDLE_DOCURL from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-ContactAddress", "BUNDLE_CONTACTADDRESS from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-Activator", "BUNDLE_ACTIVATOR from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-UpdateLocation", "BUNDLE_UPDATELOCATION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("specification-version", "PACKAGE_SPECIFICATION_VERSION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("processor", "BUNDLE_NATIVECODE_PROCESSOR from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("osname", "BUNDLE_NATIVECODE_OSNAME from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("osversion", "BUNDLE_NATIVECODE_OSVERSION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("language", "BUNDLE_NATIVECODE_LANGUAGE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-RequiredExecutionEnvironment", "BUNDLE_REQUIREDEXECUTIONENVIRONMENT from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-SymbolicName", "BUNDLE_SYMBOLICNAME from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("singleton", "SINGLETON_DIRECTIVE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("fragment-attachment", "FRAGMENT_ATTACHMENT_DIRECTIVE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("always", "FRAGMENT_ATTACHMENT_ALWAYS from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("resolve-time", "FRAGMENT_ATTACHMENT_RESOLVETIME from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("never", "FRAGMENT_ATTACHMENT_NEVER from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-Localization", "BUNDLE_LOCALIZATION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("OSGI-INF/l10n/bundle", "BUNDLE_LOCALIZATION_DEFAULT_BASENAME from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Require-Bundle", "REQUIRE_BUNDLE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("bundle-version", "BUNDLE_VERSION_ATTRIBUTE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Fragment-Host", "FRAGMENT_HOST from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("selection-filter", "SELECTION_FILTER_ATTRIBUTE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-ManifestVersion", "BUNDLE_MANIFESTVERSION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("version", "VERSION_ATTRIBUTE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("bundle-symbolic-name", "BUNDLE_SYMBOLICNAME_ATTRIBUTE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("resolution", "RESOLUTION_DIRECTIVE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("optional", "RESOLUTION_OPTIONAL from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("uses", "USES_DIRECTIVE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("include", "INCLUDE_DIRECTIVE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("exclude", "EXCLUDE_DIRECTIVE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("mandatory", "MANDATORY_DIRECTIVE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("visibility", "VISIBILITY_DIRECTIVE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("private", "VISIBILITY_PRIVATE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("reexport", "VISIBILITY_REEXPORT from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("extension", "EXTENSION_DIRECTIVE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("framework", "EXTENSION_FRAMEWORK from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("bootclasspath", "EXTENSION_BOOTCLASSPATH from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("Bundle-ActivationPolicy", "BUNDLE_ACTIVATIONPOLICY from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("lazy", "ACTIVATION_LAZY from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.version", "FRAMEWORK_VERSION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.vendor", "FRAMEWORK_VENDOR from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.language", "FRAMEWORK_LANGUAGE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.os.name", "FRAMEWORK_OS_NAME from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.os.version", "FRAMEWORK_OS_VERSION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.processor", "FRAMEWORK_PROCESSOR from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.executionenvironment", "FRAMEWORK_EXECUTIONENVIRONMENT from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.bootdelegation", "FRAMEWORK_BOOTDELEGATION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.system.packages", "FRAMEWORK_SYSTEMPACKAGES from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.system.packages.extra", "FRAMEWORK_SYSTEMPACKAGES_EXTRA from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.supports.framework.extension", "SUPPORTS_FRAMEWORK_EXTENSION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.supports.bootclasspath.extension", "SUPPORTS_BOOTCLASSPATH_EXTENSION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.supports.framework.fragment", "SUPPORTS_FRAMEWORK_FRAGMENT from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.supports.framework.requirebundle", "SUPPORTS_FRAMEWORK_REQUIREBUNDLE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.security", "FRAMEWORK_SECURITY from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("osgi", "FRAMEWORK_SECURITY_OSGI from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.storage", "FRAMEWORK_STORAGE from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.storage.clean", "FRAMEWORK_STORAGE_CLEAN from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("onFirstInit", "FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.library.extensions", "FRAMEWORK_LIBRARY_EXTENSIONS from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.command.execpermission", "FRAMEWORK_EXECPERMISSION from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.trust.repositories", "FRAMEWORK_TRUST_REPOSITORIES from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.windowsystem", "FRAMEWORK_WINDOWSYSTEM from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.startlevel.beginning", "FRAMEWORK_BEGINNING_STARTLEVEL from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("org.osgi.framework.bundle.parent", "FRAMEWORK_BUNDLE_PARENT from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("service.id", "SERVICE_ID from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("service.pid", "SERVICE_PID from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("service.ranking", "SERVICE_RANKING from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("service.vendor", "SERVICE_VENDOR from interface org.osgi.framework.Constants"),
            new AbstractMap.SimpleImmutableEntry<>("service.description", "SERVICE_DESCRIPTION from interface org.osgi.framework.Constants"),

            // class org.apache.sling.engine.EngineConstants
            new AbstractMap.SimpleImmutableEntry<>("javax.servlet.Filter", "FILTER_NAME from class org.apache.sling.engine.EngineConstants"),
            new AbstractMap.SimpleImmutableEntry<>("sling.filter.scope", "SLING_FILTER_SCOPE from class org.apache.sling.engine.EngineConstants"),
            new AbstractMap.SimpleImmutableEntry<>("COMPONENT", "FILTER_SCOPE_COMPONENT from class org.apache.sling.engine.EngineConstants"),
            new AbstractMap.SimpleImmutableEntry<>("ERROR", "FILTER_SCOPE_ERROR from class org.apache.sling.engine.EngineConstants"),
            new AbstractMap.SimpleImmutableEntry<>("INCLUDE", "FILTER_SCOPE_INCLUDE from class org.apache.sling.engine.EngineConstants"),
            new AbstractMap.SimpleImmutableEntry<>("FORWARD", "FILTER_SCOPE_FORWARD from class org.apache.sling.engine.EngineConstants"),
            new AbstractMap.SimpleImmutableEntry<>("REQUEST", "FILTER_SCOPE_REQUEST from class org.apache.sling.engine.EngineConstants"),

            // class com.adobe.granite.workflow.event.WorkflowEvent
            new AbstractMap.SimpleImmutableEntry<>("com/adobe/granite/workflow/event", "EVENT_TOPIC from class com.adobe.granite.workflow.event.WorkflowEvent")
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private ConstantsChecker() {
        // empty
    }

    public static boolean isAnnotationConstant(String value) {
        return ANNOTATION_CONSTANTS.containsKey(value);
    }

    public static String getAnnotationMessageForConstant(String value) {
        return ANNOTATION_CONSTANTS.get(value);
    }

    public static String getMessageForConstant(String value) {
        return CONSTANTS.get(value);
    }

    public static boolean isConstant(String value) {
        return CONSTANTS.containsKey(value);
    }

}
