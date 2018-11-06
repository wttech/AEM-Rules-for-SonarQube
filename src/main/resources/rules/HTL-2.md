HTL Templates should be placed in separate files. This helps to understand which code is meant to render a component and which code is re-used as a template. Additionally, component-specific code can be safely removed. It should be immediately obvious whether or not some code is re-used across the codebase.

== Noncompliant Code Example
``
<template data-sly-template.image="${@ source, description}">
    <img src="${source}" alt="${description}" title="${description}"/>
</template>
 
<div class="image-component" data-sly-use.model="com.example.Image">
    <sly data-sly-call="${image @ source=model.src, description=model.altText}"
</div>
``
