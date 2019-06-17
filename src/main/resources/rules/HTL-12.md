Use the most restrictive HTL context possible.

== Noncompliant Code Example
`<div data-index-number="${model.index}"></div>`

== Compliant Solution
`<div data-index-number="${model.index @ context='number'}"></div>`

Available markup context:
* html          - Use this in case you want to output HTMLRemoves markup that may contain XSS risks
* text          - Use this for simple HTML contentEncodes all HTML
* elementName   - Allows only element names that are white-listed, outputs 'div' otherwise -
* attributeName - Outputs nothing if the value doesn't correspond to the HTML attribute name syntaxdoesn't allow 'style' and 'on*' attributes
* attribute     - Applies HTML attribute escaping
* uri           - Outputs nothing if the value contains XSS risks
* scriptToken   - Outputs nothing if the value doesn't correspond to an Identifier, String literal or Numeric literal JavaScript token
* scriptString  - Applies JavaScript string escaping
* scriptComment - Context for Javascript block comments. Outputs nothing if value is trying to break out of the comment context
* scriptRegExp  - Applies JavaScript regular expression escaping
* styleToken    - Outputs nothing if the value doesn't correspond to the CSS token syntax
* styleString   - Applies CSS string escaping
* styleComment  - Context for CSS comments. Outputs nothing if value is trying to break out of the comment context
* comment       - Applies HTML comment escaping
* number        - Outputs zero if the value is not a number
* unsafe        - Use this at your own risk, this disables XSS protection completely
Source : https://github.com/adobe/htl-spec/blob/master/SPECIFICATION.md#121-display-context
