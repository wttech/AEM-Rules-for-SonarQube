HTL provides implicit variables in data-sly-list and data-sly-repeat blocks.
Try to avoid them and use explicit names clarifying the role of the objects instead.

== Noncompliant Code Example
````
<ul data-sly-list="${model.downloadableFiles}"> 
    <li>
        <a href="${item.url}">${item.name}</a>
    </li>
</ul>
````
== Compliant Solution
```
<ul data-sly-list.file="${model.downloadableFiles}">
    <li>
        <a href="${file.url}">${file.name}</a>
    </li>
</ul>
```
